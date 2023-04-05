package com.example.placefinder.domain.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Document {

    private String id;

    @JsonProperty("address_name")
    private String addressName;

    @JsonProperty("category_group_code")
    private String categoryGroupCode;

    private String distance;

    private String phone;

    @JsonProperty("place_name")
    private String placeName;

    @JsonProperty("place_url")
    private String placeUrl;

    @JsonProperty("road_address_name")
    private String roadAddressName;

    private double x;

    private double y;

    public static Document sample() {
        return new Document(
                "25681476",
                "대구 동구 율하동 1434",
                "FD6",
                "",
                "",
                "우야지막창",
                "http://place.map.kakao.com/25681476",
                "대구 동구 안심로22길 54",
                128.697495794721,
                35.8631344324926
        );
    }

}
