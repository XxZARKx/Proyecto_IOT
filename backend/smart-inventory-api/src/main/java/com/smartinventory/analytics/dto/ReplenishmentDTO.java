package com.smartinventory.analytics.dto;

public record ReplenishmentDTO(Long productId, String productName, String sku, int currentStock, int suggestedQuantity, String reason) {
}
