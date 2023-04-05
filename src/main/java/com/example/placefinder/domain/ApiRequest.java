package com.example.placefinder.domain;

import com.example.placefinder.domain.kakao.KakaoPlaceSearch;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Getter
@AllArgsConstructor
public class ApiRequest {

    private String url;

    private HttpHeaders headers;

    private MultiValueMap<String, String> queryParams;

    private Class clazz;

    public static ApiRequest sample() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(PlaceFinderConst.CONTENT_TYPE_HEADER_KEY, MediaType.APPLICATION_FORM_URLENCODED_VALUE);

        MultiValueMap queryParams = new LinkedMultiValueMap<>();
        queryParams.add(PlaceFinderConst.QUERY_PARAMETER_KEY, "곱창");

        return new ApiRequest("http://place.map.kakao.com/25681476", headers, queryParams, KakaoPlaceSearch.class);
    }

}
