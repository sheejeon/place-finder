package com.example.placefinder.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KeywordLog {

    private String keywordLogId;

    private String keyword;

    private long createdAt;

    public KeywordLog(String keyword) {
        this.keywordLogId = UUID.randomUUID().toString();
        this.keyword = keyword;
        this.createdAt = System.currentTimeMillis();
    }
}
