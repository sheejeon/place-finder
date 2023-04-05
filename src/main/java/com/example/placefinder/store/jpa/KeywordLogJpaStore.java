package com.example.placefinder.store.jpa;

import com.example.placefinder.domain.KeywordLog;
import com.example.placefinder.store.jpa.jpo.KeywordLogJpo;
import com.example.placefinder.store.jpa.repository.KeywordLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class KeywordLogJpaStore {

    private final KeywordLogRepository keywordLogRepository;

    public void save(KeywordLog keywordLog) {
        keywordLogRepository.save(new KeywordLogJpo(keywordLog));
    }
}
