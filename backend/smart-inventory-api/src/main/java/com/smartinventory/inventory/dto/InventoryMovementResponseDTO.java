package com.smartinventory.inventory.dto;

import com.smartinventory.inventory.model.MovementType;

import java.time.LocalDateTime;

public record InventoryMovementResponseDTO(
        Long id,
        Long productId,
        String productName,
        String sku,
        MovementType movementType,
        Integer quantity,
        Integer previousStock,
        Integer newStock,
        String reason,
        String userName,
        String source,
        LocalDateTime createdAt
) {
}
