package com.smartinventory.analytics.dto;

public record LowRotationDTO(Long productId, String productName, String sku, long exitsLast30Days, int currentStock) {
}
