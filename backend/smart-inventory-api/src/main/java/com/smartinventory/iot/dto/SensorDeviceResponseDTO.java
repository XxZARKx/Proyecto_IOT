package com.smartinventory.iot.dto;

import com.smartinventory.iot.model.DeviceStatus;
import com.smartinventory.iot.model.SensorType;

import java.time.LocalDateTime;

public record SensorDeviceResponseDTO(Long id, String code, String name, SensorType sensorType, String location, DeviceStatus status, LocalDateTime createdAt) {
}
