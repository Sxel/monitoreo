package com.example.challengeMonitoreo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "plant")
@Builder
public class Plant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de la planta no puede estar vacío")
    private String name;

    @NotBlank(message = "El país no puede estar vacío")
    private String country;

    @Min(value = 0, message = "El número de lecturas no puede ser negativo")
    private int readings;

    @Min(value = 0, message = "El número de alertas medias no puede ser negativo")
    private int mediumAlerts;

    @Min(value = 0, message = "El número de alertas rojas no puede ser negativo")
    private int redAlerts;
    }