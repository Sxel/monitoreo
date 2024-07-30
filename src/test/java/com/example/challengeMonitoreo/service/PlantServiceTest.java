package com.example.challengeMonitoreo.service;

import com.example.challengeMonitoreo.dto.PlantWithCountryDto;
import com.example.challengeMonitoreo.exception.ResourceNotFoundException;
import com.example.challengeMonitoreo.model.Country;
import com.example.challengeMonitoreo.model.Plant;
import com.example.challengeMonitoreo.repository.PlantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class PlantServiceTest {

    @Mock
    private PlantRepository plantRepository;

    @Mock
    private CountryService countryService;

    @InjectMocks
    private PlantService plantService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllPlants() {
        Plant plant1 = Plant.builder().id(1L).name("Plant 1").country("Country 1").readings(10).mediumAlerts(2).redAlerts(1).build();
        Plant plant2 = Plant.builder().id(2L).name("Plant 2").country("Country 2").readings(15).mediumAlerts(3).redAlerts(2).build();
        Page<Plant> plantPage = new PageImpl<>(Arrays.asList(plant1, plant2));

        when(plantRepository.findAll(any(PageRequest.class))).thenReturn(plantPage);

        Country country1 = Country.builder().name(new Country.Name("Country 1")).flags(new Country.Flags("flag1.png")).build();
        Country country2 = Country.builder().name(new Country.Name("Country 2")).flags(new Country.Flags("flag2.png")).build();

        when(countryService.getCountryByName("Country 1")).thenReturn(Mono.just(country1));
        when(countryService.getCountryByName("Country 2")).thenReturn(Mono.just(country2));

        Page<PlantWithCountryDto> result = plantService.getAllPlants(0, 10);

        assertEquals(2, result.getContent().size());
        assertEquals("Plant 1", result.getContent().get(0).getName());
        assertEquals("Country 1", result.getContent().get(0).getCountry());
        assertEquals("flag1.png", result.getContent().get(0).getFlag());
    }

    @Test
    void createPlant() {
        Plant plant = Plant.builder().id(1L).name("New Plant").country("Country").readings(0).mediumAlerts(0).redAlerts(0).build();
        when(plantRepository.save(any(Plant.class))).thenReturn(plant);

        Plant result = plantService.createPlant(plant);

        assertNotNull(result);
        assertEquals("New Plant", result.getName());
    }

    @Test
    void updatePlant() {
        Plant existingPlant = Plant.builder().id(1L).name("Existing Plant").country("Country").readings(10).mediumAlerts(2).redAlerts(1).build();
        Plant updatedPlant = Plant.builder().id(1L).name("Updated Plant").country("New Country").readings(15).mediumAlerts(3).redAlerts(2).build();

        when(plantRepository.findById(1L)).thenReturn(Optional.of(existingPlant));
        when(plantRepository.save(any(Plant.class))).thenReturn(updatedPlant);

        Plant result = plantService.updatePlant(1L, updatedPlant);

        assertNotNull(result);
        assertEquals("Updated Plant", result.getName());
        assertEquals("New Country", result.getCountry());
    }

    @Test
    void updatePlant_NotFound() {
        when(plantRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> plantService.updatePlant(1L, Plant.builder().build()));
    }

    @Test
    void deletePlant() {
        Plant plant = Plant.builder().id(1L).name("Plant to Delete").country("Country").readings(10).mediumAlerts(2).redAlerts(1).build();
        when(plantRepository.findById(1L)).thenReturn(Optional.of(plant));

        plantService.deletePlant(1L);

        verify(plantRepository, times(1)).delete(plant);
    }

    @Test
    void deletePlant_NotFound() {
        when(plantRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> plantService.deletePlant(1L));
    }
}