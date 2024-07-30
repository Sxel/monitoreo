package com.example.challengeMonitoreo.service;

import com.example.challengeMonitoreo.model.Plant;
import com.example.challengeMonitoreo.model.Sensor;
import com.example.challengeMonitoreo.repository.PlantRepository;
import com.example.challengeMonitoreo.repository.SensorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

class MonitoringServiceTest {

    @Mock
    private PlantRepository plantRepository;

    @Mock
    private SensorRepository sensorRepository;

    @InjectMocks
    private MonitoringService monitoringService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getTotalReadings() {
        List<Plant> plants = Arrays.asList(
                Plant.builder().id(1L).name("Plant 1").country("Country 1").readings(10).mediumAlerts(2).redAlerts(1).build(),
                Plant.builder().id(2L).name("Plant 2").country("Country 2").readings(15).mediumAlerts(3).redAlerts(2).build()
        );
        when(plantRepository.findAll()).thenReturn(plants);

        int result = monitoringService.getTotalReadings();

        assertEquals(25, result);
    }

    @Test
    void getTotalMediumAlerts() {
        List<Plant> plants = Arrays.asList(
                Plant.builder().id(1L).name("Plant 1").country("Country 1").readings(10).mediumAlerts(2).redAlerts(1).build(),
                Plant.builder().id(2L).name("Plant 2").country("Country 2").readings(15).mediumAlerts(3).redAlerts(2).build()
        );
        when(plantRepository.findAll()).thenReturn(plants);

        int result = monitoringService.getTotalMediumAlerts();

        assertEquals(5, result);
    }

    @Test
    void getTotalRedAlerts() {
        List<Plant> plants = Arrays.asList(
                Plant.builder().id(1L).name("Plant 1").country("Country 1").readings(10).mediumAlerts(2).redAlerts(1).build(),
                Plant.builder().id(2L).name("Plant 2").country("Country 2").readings(15).mediumAlerts(3).redAlerts(2).build()
        );
        when(plantRepository.findAll()).thenReturn(plants);

        int result = monitoringService.getTotalRedAlerts();

        assertEquals(3, result);
    }

    @Test
    void getDisabledSensorsCount() {
        List<Sensor> disabledSensors = Arrays.asList(
                Sensor.builder().id(1L).name("Sensor 1").enabled(false).build(),
                Sensor.builder().id(2L).name("Sensor 2").enabled(false).build()
        );
        doReturn(disabledSensors).when(sensorRepository).findByEnabled(false);
        int result = monitoringService.getDisabledSensorsCount();

        assertEquals(2, result);
    }
}