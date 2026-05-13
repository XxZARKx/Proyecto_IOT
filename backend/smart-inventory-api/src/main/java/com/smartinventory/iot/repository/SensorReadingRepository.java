package com.smartinventory.iot.repository;

import com.smartinventory.iot.model.SensorReading;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SensorReadingRepository extends JpaRepository<SensorReading, Long> {
    List<SensorReading> findTop50ByOrderByCreatedAtDesc();
}
