package com.example.placefinder.store.jpa;

import com.example.placefinder.domain.KeywordCounter;
import com.example.placefinder.domain.KeywordCounterList;
import com.example.placefinder.store.jpa.jpo.KeywordCounterJpo;
import com.example.placefinder.store.jpa.repository.KeywordCounterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
@RequiredArgsConstructor
public class KeywordCounterJpaStore {

    private final KeywordCounterRepository keywordCounterRepository;

    public KeywordCounterList findAll(int size) {
        return new KeywordCounterList(
                StreamSupport.stream(keywordCounterRepository.findAllByOrderByCountDesc().spliterator(), false)
                .map(KeywordCounterJpo::toDomain)
                .limit(size)
                .collect(Collectors.toList()));
    }

    public Optional<KeywordCounter> find(String keyword) {
        return keywordCounterRepository.findById(keyword).map(KeywordCounterJpo::toDomain);
    }

    public void save(KeywordCounter keywordCounter) {
        keywordCounterRepository.save(new KeywordCounterJpo(keywordCounter));
    }
}
