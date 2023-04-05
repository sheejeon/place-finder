package com.example.placefinder.service;

import com.example.placefinder.domain.*;
import com.example.placefinder.domain.kakao.KakaoPlaceSearch;
import com.example.placefinder.domain.naver.NaverPlaceSearch;
import com.example.share.service.WebClientService;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class PlaceApiService {

    @Value("${place-finder.api.kakao.url}")
    private String kakaoUrl;

    @Value("${place-finder.api.kakao.authorization}")
    private String kakaoAuthorization;

    @Value("${place-finder.api.naver.url}")
    private String naverUrl;

    @Value("${place-finder.api.naver.naver-client-id}")
    private String naverClientId;

    @Value("${place-finder.api.naver.naver-client-secret-id}")
    private String naverClientSecretId;

    @Value("${place-finder.api.google.api-key}")
    private String googleApiKey;

    @Value("${place-finder.api.google.use:false}")
    private boolean useGoogleApi;

    private final WebClientService webClientService;

    public PlaceList kakaoPlaceSearch(String keyword) {
        ApiRequest apiRequest = getKakaoRequest(kakaoUrl, keyword, KakaoPlaceSearch.class);
        KakaoPlaceSearch kakaoPlaceSearch = webClientService.call(apiRequest);

        return new PlaceList(kakaoPlaceSearch.getDocuments().stream()
                .map(document ->
                        new Place(
                                document.getPlaceName(),
                                document.getPlaceUrl(),
                                document.getRoadAddressName(),
                                getLocation(document.getRoadAddressName(), document.getX(), document.getY())))
                .toList());
    }

    public PlaceList naverPlaceSearch(String keyword) {
        ApiRequest apiRequest = getNaverRequest(naverUrl, keyword, NaverPlaceSearch.class);
        NaverPlaceSearch naverPlaceSearch = webClientService.call(apiRequest);

        return new PlaceList(naverPlaceSearch.getItems().stream()
                .map(item ->
                        new Place(
                                item.getTitle(),
                                item.getLink(),
                                item.getRoadAddress(),
                                getLocation(item.getRoadAddress(), item.getMapx(), item.getMapy())))
                .toList());
    }

    private Location getLocation(String address, double defaultLat, double defaultLng) {
        if (useGoogleApi) {
            try {
                GeoApiContext context = new GeoApiContext.Builder()
                    .apiKey(googleApiKey)
                    .build();

                GeocodingResult[] results = GeocodingApi.geocode(context, address).await();

                double lat = results.length > 0 ? results[0].geometry.location.lat : defaultLat;
                double lng = results.length > 0 ? results[0].geometry.location.lng : defaultLng;

                return new Location(lat, lng);

            } catch (ApiException | InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
        return new Location(defaultLat, defaultLng);
    }

    private ApiRequest getKakaoRequest(String url, String keyword, Class clazz) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(PlaceFinderConst.CONTENT_TYPE_HEADER_KEY, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        headers.add(PlaceFinderConst.KAKAO_AUTHORIZATION_HEADER_KEY, kakaoAuthorization);

        MultiValueMap queryParams = new LinkedMultiValueMap<>();
        queryParams.add(PlaceFinderConst.QUERY_PARAMETER_KEY, keyword);

        return new ApiRequest(url, headers, queryParams, clazz);
    }

    private ApiRequest getNaverRequest(String url, String keyword, Class clazz) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(PlaceFinderConst.CONTENT_TYPE_HEADER_KEY, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        headers.add(PlaceFinderConst.NAVER_CLIENT_ID_HEADER_KEY, naverClientId);
        headers.add(PlaceFinderConst.NAVER_CLIENT_SECRET_HEADER_KEY, naverClientSecretId);

        MultiValueMap queryParams = new LinkedMultiValueMap<>();
        queryParams.add(PlaceFinderConst.QUERY_PARAMETER_KEY, keyword);
        queryParams.add(PlaceFinderConst.NAVER_DISPLAY_PARAMETER_KEY, PlaceFinderConst.NAVER_MAX_SIZE);

        return new ApiRequest(url, headers, queryParams, clazz);
    }
}
