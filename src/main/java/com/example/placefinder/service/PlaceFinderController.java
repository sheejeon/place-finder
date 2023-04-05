package com.example.placefinder.service;

import com.example.placefinder.domain.KeywordCounterList;
import com.example.placefinder.domain.PlaceList;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("places")
public class PlaceFinderController {

    private final PlaceFinderService placeFinderService;

    @GetMapping
    public PlaceList findAll(
        @RequestParam String keyword,
        @RequestParam(defaultValue = "10", required = false) int size
    ) {
        return placeFinderService.findAll(keyword, size);
    }

    @GetMapping("ranking")
    public KeywordCounterList ranking(
            @RequestParam(defaultValue = "10", required = false) int size
    ) {
        return placeFinderService.ranking(size);
    }
}
