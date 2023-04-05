package com.example.placefinder.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Place {

    private String name;

    private String url;

    private String roadAddress;

    private Location location;

    public static Place sample() {
        return new Place("우야지막창", "http://place.map.kakao.com/25681476", "대구 동구 안심로22길 54", Location.sample());
    }

}
