package com.example.placefinder.store.jpa.repository;

import com.example.placefinder.store.jpa.jpo.KeywordLogJpo;
import org.springframework.data.repository.CrudRepository;

public interface KeywordLogRepository extends CrudRepository<KeywordLogJpo, String> {
}
