package com.smartinventory.iot.dto;

import com.smartinventory.inventory.model.MovementType;
import com.smartinventory.iot.model.SensorType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SensorReadingRequestDTO(
        @NotBlank String deviceCode,
        @NotBlank String productSku,
        @NotNull SensorType sensorType,
        @NotNull @Min(1) Integer quantityDetected,
        MovementType movementType,
        String rawValue,
        String location,
        Boolean processAsMovement
) {
}
