package com.example.challengeMonitoreo.service;

import com.example.challengeMonitoreo.dto.PlantWithCountryDto;
import com.example.challengeMonitoreo.exception.ResourceNotFoundException;
import com.example.challengeMonitoreo.model.Country;
import com.example.challengeMonitoreo.model.Plant;
import com.example.challengeMonitoreo.repository.PlantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlantService {
    @Autowired
    private PlantRepository plantRepository;
    @Autowired
    private CountryService countryService;

    public Page<PlantWithCountryDto> getAllPlants(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Plant> plantPage = plantRepository.findAll(pageable);

        List<PlantWithCountryDto> plantWithCountryDTOs = plantPage.stream()
                .map(plant -> {
                    PlantWithCountryDto dto = new PlantWithCountryDto();
                    dto.setId(plant.getId());
                    dto.setName(plant.getName());
                    dto.setReadings(plant.getReadings());
                    dto.setMediumAlerts(plant.getMediumAlerts());
                    dto.setRedAlerts(plant.getRedAlerts());

                    // Obtener el país y la bandera
                    Country country = countryService.getCountryByName(plant.getCountry()).block();
                    System.out.println("Country: " + country);
                    if (country != null) {
                        dto.setCountry(country.getName().getCommon());
                        dto.setFlag(country.getFlags().getPng());
                    } else {
                        dto.setCountry("Unknown");
                        dto.setFlag("Unknown");
                    }

                    return dto;
                }).collect(Collectors.toList());

        return new PageImpl<>(plantWithCountryDTOs, pageable, plantPage.getTotalElements());
    }

    public Plant createPlant(Plant plant) {
        return plantRepository.save(plant);
    }


    public Plant updatePlant(Long id, Plant plantDetails) {
        Plant plant = plantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plant not found"));
        plant.setName(plantDetails.getName());
        plant.setCountry(plantDetails.getCountry());
        plant.setReadings(plantDetails.getReadings());
        plant.setMediumAlerts(plantDetails.getMediumAlerts());
        plant.setRedAlerts(plantDetails.getRedAlerts());
        return plantRepository.save(plant);
    }

    public void deletePlant(Long id) {
        Plant plant = plantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plant not found"));
        plantRepository.delete(plant);
    }
}