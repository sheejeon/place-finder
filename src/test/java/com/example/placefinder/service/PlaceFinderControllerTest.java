package com.example.placefinder.service;

import com.example.placefinder.domain.KeywordCounterList;
import com.example.placefinder.domain.PlaceList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringRunner.class)
@WebMvcTest(PlaceFinderController.class)
public class PlaceFinderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlaceFinderService placeFinderService;

    @MockBean
    private PlaceApiService placeApiService;


    @Test
    public void findAll() throws Exception {
        given(placeFinderService.findAll(
            anyString(),
            anyInt()
        ))
            .willReturn(PlaceList.sample());

        mockMvc.perform(get("/places?keyword=곱창"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(PlaceList.sample().get(0).getName())))
                .andExpect(jsonPath("$[0].url", is(PlaceList.sample().get(0).getUrl())))
                .andExpect(jsonPath("$[0].roadAddress", is(PlaceList.sample().get(0).getRoadAddress())))
                .andExpect(jsonPath("$[0].location.latitude", is(PlaceList.sample().get(0).getLocation().getLatitude())))
                .andExpect(jsonPath("$[0].location.longitude", is(PlaceList.sample().get(0).getLocation().getLongitude())));

        verify(placeFinderService).findAll(
            anyString(),
            anyInt()
        );
    }

    @Test
    public void ranking() throws Exception {
        given(placeFinderService.ranking(
                anyInt()
        ))
                .willReturn(KeywordCounterList.sample());

        mockMvc.perform(get("/places/ranking"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].keyword", is(KeywordCounterList.sample().get(0).getKeyword())))
                .andExpect(jsonPath("$[0].count", is(KeywordCounterList.sample().get(0).getCount())));

        verify(placeFinderService).ranking(
                anyInt()
        );
    }

}
