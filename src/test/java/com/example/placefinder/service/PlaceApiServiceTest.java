package com.example.placefinder.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.example.placefinder.domain.ApiRequest;
import com.example.placefinder.domain.PlaceList;
import com.example.placefinder.domain.kakao.KakaoPlaceSearch;
import com.example.placefinder.domain.naver.NaverPlaceSearch;
import com.example.share.service.WebClientService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PlaceApiServiceTest {

    @InjectMocks
    private PlaceApiService placeApiService;

    @Mock
    private WebClientService webClientService;

    @Test
    public void kakaoCall() {
        when(webClientService.call(
                any(ApiRequest.class)
        ))
                .thenReturn(KakaoPlaceSearch.sample());

        PlaceList actual = placeApiService.kakaoPlaceSearch("우야지곱창");

        Assert.assertFalse(actual.isEmpty());
    }

    @Test
    public void naverCall() {
        when(webClientService.call(
                any(ApiRequest.class)
        ))
                .thenReturn(NaverPlaceSearch.sample());

        PlaceList actual = placeApiService.naverPlaceSearch("우야지곱창");

        Assert.assertFalse(actual.isEmpty());
    }

}
