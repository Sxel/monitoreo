package com.example.challengeMonitoreo.service;

import com.example.challengeMonitoreo.model.Country;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class CountryService {
    private final WebClient webClient;

    public CountryService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://restcountries.com/v3.1").build();
    }

    public Mono<List<Country>> getAllCountries() {
        return webClient.get()
                .uri("/all?fields=name,flags")
                .retrieve()
                .bodyToFlux(Country.class)
                .collectList();
    }

    public Mono<Country> getCountryByName(String countryName) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/name/{name}")
                        .queryParam("fields", "name,flags")
                        .build(countryName))
                .retrieve()
                .bodyToFlux(Country.class)
                .next(); // Obtiene el primer país si está presente
    }
}