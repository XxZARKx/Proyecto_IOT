package com.smartinventory.analytics.dto;

public record DemandPredictionDTO(Long productId, String productName, String sku, double averageDailyDemand, long projected30Days) {
}
