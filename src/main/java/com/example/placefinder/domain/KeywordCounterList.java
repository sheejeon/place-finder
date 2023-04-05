package com.example.placefinder.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class KeywordCounterList extends ArrayList<KeywordCounter> {
    public KeywordCounterList(Collection<? extends KeywordCounter> c) {
        super(c);
    }

    public static KeywordCounterList sample() {
        return new KeywordCounterList(Collections.singletonList(KeywordCounter.sample()));
    }
}
