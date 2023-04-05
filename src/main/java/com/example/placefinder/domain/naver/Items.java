package com.example.placefinder.domain.naver;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Items {

    private String title;

    private String link;

    private String category;

    private String description;

    private String telephone;

    private String address;

    private String roadAddress;

    private double mapx;

    private double mapy;

    public static Items sample() {
        return new Items(
            "<b>우야지막창</b> 동<b>대구</b>역점",
            "http://wooyaji.modoo.at",
            "육류,고기요리>곱창,막창,양",
            "",
            "",
            "대구광역시 동구 신천동 334-14 1층 우야지막창",
            "대구광역시 동구 동부로30길 34 1층 우야지막창",
            456963,
            363978
        );
    }
}
