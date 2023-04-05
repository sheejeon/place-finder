package com.example.placefinder.store.jpa.jpo;

import com.example.placefinder.domain.KeywordCounter;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name="keyword_counter")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KeywordCounterJpo {

    @Id
    private String keyword;

    private int count;

    public KeywordCounterJpo(KeywordCounter keywordCounter) {
        this.keyword = keywordCounter.getKeyword();
        this.count = keywordCounter.getCount();
    }

    public KeywordCounter toDomain() {
        return new KeywordCounter(keyword, count);
    }
}
