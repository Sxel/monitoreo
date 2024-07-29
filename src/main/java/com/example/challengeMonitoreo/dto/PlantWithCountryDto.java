package com.example.challengeMonitoreo.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlantWithCountryDto {
    private Long id;
    private String name;
    private String country;
    private String flag;
    private int readings;
    private int mediumAlerts;
    private int redAlerts;
}
