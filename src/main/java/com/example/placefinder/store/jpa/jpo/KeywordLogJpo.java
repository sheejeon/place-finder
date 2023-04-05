package com.example.placefinder.store.jpa.jpo;

import com.example.placefinder.domain.KeywordLog;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name="keyword_log")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KeywordLogJpo {

    @Id
    private String keywordLogId;

    private String keyword;

    private long createdAt;

    public KeywordLogJpo(KeywordLog keywordLog) {
        this.keywordLogId = keywordLog.getKeywordLogId();
        this.keyword = keywordLog.getKeyword();
        this.createdAt = keywordLog.getCreatedAt();
    }

    public KeywordLog toDomain() {
        return new KeywordLog(keywordLogId, keyword, createdAt);
    }
}
