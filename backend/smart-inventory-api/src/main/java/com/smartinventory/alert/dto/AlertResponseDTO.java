package com.smartinventory.alert.dto;

import com.smartinventory.alert.model.AlertStatus;
import com.smartinventory.alert.model.AlertType;

import java.time.LocalDateTime;

public record AlertResponseDTO(
        Long id,
        Long productId,
        String productName,
        AlertType alertType,
        String message,
        AlertStatus status,
        LocalDateTime createdAt,
        LocalDateTime resolvedAt
) {
}
