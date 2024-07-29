package com.example.challengeMonitoreo.controller;

import com.example.challengeMonitoreo.model.Country;
import com.example.challengeMonitoreo.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CountryController {
    @Autowired
    private CountryService countryService;

    @GetMapping("countries")
    public Mono<List<Country>> getAllCountries() {
        return countryService.getAllCountries();
    }
    @GetMapping("{countryName}")
    public Mono<ResponseEntity<Country>> getCountryByName(@PathVariable String countryName) {
        return countryService.getCountryByName(countryName)
                .map(country -> ResponseEntity.ok(country))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}