package com.smartinventory.product.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductRequestDTO(
        @NotBlank String name,
        String description,
        @NotBlank String sku,
        String barcode,
        @NotNull @DecimalMin("0.0") BigDecimal unitPrice,
        @NotNull @Min(0) Integer currentStock,
        @NotNull @Min(0) Integer minimumStock,
        @NotNull @Min(1) Integer maximumStock,
        @NotNull Long categoryId
) {
}
