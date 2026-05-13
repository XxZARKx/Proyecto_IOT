package com.smartinventory.iot.repository;

import com.smartinventory.iot.model.SensorDevice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SensorDeviceRepository extends JpaRepository<SensorDevice, Long> {
    Optional<SensorDevice> findByCode(String code);
    boolean existsByCode(String code);
}
