package com.smartinventory.inventory.dto;

import com.smartinventory.inventory.model.MovementType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record InventoryMovementRequestDTO(
        @NotNull Long productId,
        @NotNull MovementType movementType,
        @NotNull @Min(0) Integer quantity,
        String reason
) {
}
