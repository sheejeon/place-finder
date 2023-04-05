package com.example.placefinder.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KeywordCounter {

    private String keyword;

    private int count;

    public static KeywordCounter sample() {
        return new KeywordCounter("곱창", 5);
    }

}
