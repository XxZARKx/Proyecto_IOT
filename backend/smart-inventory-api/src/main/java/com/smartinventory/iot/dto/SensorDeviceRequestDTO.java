package com.smartinventory.iot.dto;

import com.smartinventory.iot.model.DeviceStatus;
import com.smartinventory.iot.model.SensorType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SensorDeviceRequestDTO(
        @NotBlank String code,
        @NotBlank String name,
        @NotNull SensorType sensorType,
        String location,
        DeviceStatus status
) {
}
