package com.example.placefinder.service;

import com.example.placefinder.domain.KeywordCounterList;
import com.example.placefinder.domain.KeywordLog;
import com.example.placefinder.domain.PlaceList;
import com.example.placefinder.store.jpa.KeywordCounterJpaStore;
import com.example.placefinder.store.jpa.KeywordLogJpaStore;
import com.example.share.service.WebClientService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PlaceFinderServiceTest {

    @InjectMocks
    private PlaceFinderService placeFinderService;

    @Mock
    private PlaceApiService placeApiService;

    @Mock
    private WebClientService webClientService;

    @Mock
    private KeywordCounterJpaStore keywordCounterJpaStore;

    @Mock
    private KeywordLogJpaStore keywordLogJpaStore;

    @Test
    public void findAll() {
        when(placeApiService.kakaoPlaceSearch(
                anyString()
        ))
                .thenReturn(PlaceList.sample());

        when(placeApiService.naverPlaceSearch(
                anyString()
        ))
                .thenReturn(PlaceList.sample());

        PlaceList actual = placeFinderService.findAll("우야지곱창", 10);

        Assert.assertFalse(actual.isEmpty());
    }

    @Test
    public void ranking() {
        when(keywordCounterJpaStore.findAll(
                anyInt()
        ))
            .thenReturn(KeywordCounterList.sample());

        KeywordCounterList actual = placeFinderService.ranking(10);

        Assert.assertFalse(actual.isEmpty());
    }

    @Test
    public void register() {
        placeFinderService.register("곱창");
        verify(keywordLogJpaStore).save(any(KeywordLog.class));
    }


}
