package com.example.challengeMonitoreo.controller;

import com.example.challengeMonitoreo.service.MonitoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/monitoring")
public class MonitoringController {
    @Autowired
    private MonitoringService monitoringService;

    @GetMapping("/readings")
    public int getTotalReadings() {
        return monitoringService.getTotalReadings();
    }

    @GetMapping("/medium-alerts")
    public int getTotalMediumAlerts() {
        return monitoringService.getTotalMediumAlerts();
    }

    @GetMapping("/red-alerts")
    public int getTotalRedAlerts() {
        return monitoringService.getTotalRedAlerts();
    }

    @GetMapping("/disabled-sensors")
    public int getDisabledSensorsCount() {
        return monitoringService.getDisabledSensorsCount();
    }
}