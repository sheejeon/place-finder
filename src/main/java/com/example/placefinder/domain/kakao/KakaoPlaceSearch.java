package com.example.placefinder.domain.kakao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KakaoPlaceSearch  {

    private List<Document> documents;

    public static KakaoPlaceSearch sample() {
        return new KakaoPlaceSearch(List.of(Document.sample()));
    }

}
