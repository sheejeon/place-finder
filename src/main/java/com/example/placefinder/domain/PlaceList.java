package com.example.placefinder.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PlaceList extends ArrayList<Place> {
    public PlaceList(Collection<? extends Place> c) {
        super(c);
    }

    public static PlaceList sample() {
        return new PlaceList(Collections.singletonList(Place.sample()));
    }
}
