package com.smartinventory.product.dto;

import com.smartinventory.product.model.ProductStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductResponseDTO(
        Long id,
        String name,
        String description,
        String sku,
        String barcode,
        BigDecimal unitPrice,
        Integer currentStock,
        Integer minimumStock,
        Integer maximumStock,
        Long categoryId,
        String categoryName,
        ProductStatus status,
        LocalDateTime createdAt
) {
}
