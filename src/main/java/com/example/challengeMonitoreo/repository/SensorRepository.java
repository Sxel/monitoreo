package com.example.challengeMonitoreo.repository;

import com.example.challengeMonitoreo.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Long> {
    Collection<Object> findByEnabled(boolean b);
}
