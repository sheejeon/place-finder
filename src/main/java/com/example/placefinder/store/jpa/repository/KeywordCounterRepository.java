package com.example.placefinder.store.jpa.repository;

import com.example.placefinder.store.jpa.jpo.KeywordCounterJpo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface KeywordCounterRepository extends CrudRepository<KeywordCounterJpo, String> {
    List<KeywordCounterJpo> findAllByOrderByCountDesc();
}
