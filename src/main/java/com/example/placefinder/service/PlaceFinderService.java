package com.example.placefinder.service;

import com.example.placefinder.domain.*;
import com.example.placefinder.store.jpa.KeywordLogJpaStore;
import com.example.placefinder.store.jpa.KeywordCounterJpaStore;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlaceFinderService {

    private final KeywordCounterJpaStore keywordCounterStore;

    private final KeywordLogJpaStore keywordLogStore;

    private final PlaceApiService placeApiService;

    @Value("${place-finder.api.google.use:false}")
    private boolean useGoogleApi;

    public PlaceList findAll(String keyword, int size) {
        PlaceList kakaoPlaces = placeApiService.kakaoPlaceSearch(keyword);
        PlaceList naverPlaces = placeApiService.naverPlaceSearch(keyword);

        PlaceList result = sort(kakaoPlaces, naverPlaces, size);

        register(keyword);

        return result;
    }

    @Transactional
    public void register(String keyword) {
        keywordLogStore.save(new KeywordLog(keyword));
        Optional<KeywordCounter> searchNumber = keywordCounterStore.find(keyword);

        if (searchNumber.isPresent()) {
            keywordCounterStore.save(new KeywordCounter(keyword, searchNumber.get().getCount() + 1));
        } else {
            keywordCounterStore.save(new KeywordCounter(keyword, 1));
        }
    }

    public KeywordCounterList ranking(int size) {
        return keywordCounterStore.findAll(size);
    }

    private PlaceList sort(PlaceList kakaoPlaces, PlaceList naverPlaces, int size) {
        PlaceList result = new PlaceList();
        PlaceList unmergedKakaoPlaces = new PlaceList(kakaoPlaces);
        PlaceList unmergedNaverPlaces = new PlaceList(naverPlaces);

        for (Place kakaoPlace: kakaoPlaces) {
            for(Place naverPlace: naverPlaces) {
                boolean isSameLocation = isSameLocation(kakaoPlace, naverPlace);
                boolean isExceedSize = isExceedSize(result.size(), size);
                if (isSameLocation && !isExceedSize) {
                    result.add(kakaoPlace);
                    unmergedKakaoPlaces.remove(kakaoPlace);
                    unmergedNaverPlaces.remove(naverPlace);
                }
            }
        }

        for (Place kakaoPlace: unmergedKakaoPlaces) {
            if (result.size() >= size) break;
            result.add(kakaoPlace);
        }

        for (Place naverPlace: unmergedNaverPlaces) {
            if (result.size() >= size) break;
            result.add(naverPlace);
        }

        return result;
    }

    private boolean isSameLocation(Place kakaoPlace, Place naverPlace) {
        if (useGoogleApi) {
            return kakaoPlace.getLocation().equals(naverPlace.getLocation());
        } else {
            return kakaoPlace.getName().trim().equals(naverPlace.getName().trim());
        }
    }

    private boolean isExceedSize(int targetSize, int maxSize) {
        return targetSize >= maxSize;
    }
}
