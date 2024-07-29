package com.example.challengeMonitoreo.service;

import com.example.challengeMonitoreo.repository.PlantRepository;
import com.example.challengeMonitoreo.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.challengeMonitoreo.model.Plant;


@Service
public class MonitoringService {
    @Autowired
    private PlantRepository plantRepository;
    @Autowired
    private SensorRepository sensorRepository;

    public int getTotalReadings() {
        return plantRepository.findAll().stream()
                .mapToInt(Plant::getReadings)
                .sum();
    }

    public int getTotalMediumAlerts() {
        return plantRepository.findAll().stream()
                .mapToInt(Plant::getMediumAlerts)
                .sum();
    }

    public int getTotalRedAlerts() {
        return plantRepository.findAll().stream()
                .mapToInt(Plant::getRedAlerts)
                .sum();
    }

    public int getDisabledSensorsCount() {
        return sensorRepository.findByEnabled(false).size();
    }
}