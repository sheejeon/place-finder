package com.example.share.service;

import com.example.placefinder.domain.ApiRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;


import java.net.URI;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class WebClientService {

    public <T> T call(ApiRequest apiRequest) {
        WebClient webClient = WebClient.create(apiRequest.getUrl());

        return (T) webClient.get()
                .uri(getUri(apiRequest.getUrl(), apiRequest.getQueryParams()))
                .headers(h -> h.addAll(apiRequest.getHeaders()))
                .exchangeToMono(clientResponse -> {
                    if (!clientResponse.statusCode().is2xxSuccessful()) {
                        return clientResponse
                            .createException()
                            .flatMap(it -> Mono.error(new RuntimeException(clientResponse.statusCode().value() + "")));
                    }
                    return clientResponse.bodyToMono(apiRequest.getClazz());
                })
                .onErrorResume(error -> {
                    return Mono.just(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));

                })
                .block();
    }

    public URI getUri(String url, MultiValueMap params) {
        UriComponents uriComponents = UriComponentsBuilder.fromUriString(url)
                .queryParams(params)
                .encode()
                .build();
        return uriComponents.toUri();
    }

}
