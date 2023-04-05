package com.example.placefinder.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class Location {

    private double latitude;

    private double longitude;

    public static Location sample() {
        return new Location(35.8633598, 128.6975384);
    }

}
