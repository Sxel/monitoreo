package com.example.challengeMonitoreo.controller;

import com.example.challengeMonitoreo.dto.PlantWithCountryDto;
import com.example.challengeMonitoreo.model.Plant;
import com.example.challengeMonitoreo.service.PlantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/plants")
public class PlantController {
    @Autowired
    private PlantService plantService;

    @GetMapping
    public ResponseEntity<Page<PlantWithCountryDto>> getAllPlants(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<PlantWithCountryDto> result = plantService.getAllPlants(page, size);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public Plant createPlant(@Valid @RequestBody Plant plant) {
        return plantService.createPlant(plant);
    }

    @PutMapping("/{id}")
    public Plant updatePlant(@PathVariable Long id, @Valid @RequestBody Plant plantDetails) {
        return plantService.updatePlant(id, plantDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlant(@PathVariable Long id) {
        plantService.deletePlant(id);
        return ResponseEntity.ok().build();
    }
}