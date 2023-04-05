package com.example.placefinder.domain.naver;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NaverPlaceSearch  {

    private int total;

    private int display;

    private int start;

    private String lastBuildDate;

    private List<Items> items;

    public static NaverPlaceSearch sample() {
        return new NaverPlaceSearch(5, 5, 1, "Tue, 04 Apr 2023 10:34:11 +0900", List.of(Items.sample()));
    }
}
