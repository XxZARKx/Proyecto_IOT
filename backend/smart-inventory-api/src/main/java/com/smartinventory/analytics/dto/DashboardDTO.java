package com.smartinventory.analytics.dto;

import com.smartinventory.inventory.dto.InventoryMovementResponseDTO;

import java.util.List;

public record DashboardDTO(
        long totalProducts,
        long lowStockProducts,
        long outOfStockProducts,
        long activeAlerts,
        long movementsThisMonth,
        List<MovementSerieDTO> movementSeries,
        List<TopProductDTO> topProducts,
        List<LowRotationDTO> lowRotationProducts,
        List<ReplenishmentDTO> replenishmentRecommendations,
        List<InventoryMovementResponseDTO> recentMovements
) {
}
