package com.smartinventory.iot.dto;

import com.smartinventory.inventory.model.MovementType;

import java.time.LocalDateTime;

public record SensorReadingResponseDTO(
        Long id,
        String deviceCode,
        String productSku,
        String productName,
        Integer quantityDetected,
        String rawValue,
        String location,
        MovementType movementType,
        boolean processed,
        LocalDateTime createdAt
) {
}
