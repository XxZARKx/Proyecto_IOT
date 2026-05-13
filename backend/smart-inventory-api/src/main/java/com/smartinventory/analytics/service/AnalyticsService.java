package com.smartinventory.analytics.service;

import com.smartinventory.alert.service.AlertService;
import com.smartinventory.analytics.dto.*;
import com.smartinventory.inventory.model.InventoryMovement;
import com.smartinventory.inventory.model.MovementType;
import com.smartinventory.inventory.repository.InventoryMovementRepository;
import com.smartinventory.inventory.service.InventoryService;
import com.smartinventory.product.model.Product;
import com.smartinventory.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalyticsService {
    private final ProductRepository productRepository;
    private final InventoryMovementRepository movementRepository;
    private final InventoryService inventoryService;
    private final AlertService alertService;

    @Transactional(readOnly = true)
    public DashboardDTO dashboard() {
        List<Product> products = productRepository.findAll();
        return new DashboardDTO(
                products.size(),
                products.stream().filter(p -> p.getCurrentStock() <= p.getMinimumStock()).count(),
                products.stream().filter(p -> p.getCurrentStock() == 0).count(),
                alertService.countActive(),
                inventoryService.countThisMonth(),
                movementSeries(),
                topProducts(),
                lowRotation(),
                replenishment(),
                inventoryService.recent()
        );
    }

    @Transactional(readOnly = true)
    public List<TopProductDTO> topProducts() {
        return movementRepository.findByMovementTypeAndCreatedAtAfter(MovementType.SALIDA, LocalDateTime.now().minusDays(90)).stream()
                .collect(Collectors.groupingBy(InventoryMovement::getProduct, Collectors.summingLong(InventoryMovement::getQuantity)))
                .entrySet().stream()
                .sorted(Map.Entry.<Product, Long>comparingByValue().reversed())
                .limit(10)
                .map(e -> new TopProductDTO(e.getKey().getId(), e.getKey().getName(), e.getKey().getSku(), e.getValue()))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<LowRotationDTO> lowRotation() {
        LocalDateTime from = LocalDateTime.now().minusDays(30);
        Map<Product, Long> exits = movementRepository.findByMovementTypeAndCreatedAtAfter(MovementType.SALIDA, from).stream()
                .collect(Collectors.groupingBy(InventoryMovement::getProduct, Collectors.summingLong(InventoryMovement::getQuantity)));
        return productRepository.findAll().stream()
                .map(p -> new LowRotationDTO(p.getId(), p.getName(), p.getSku(), exits.getOrDefault(p, 0L), p.getCurrentStock()))
                .filter(p -> p.exitsLast30Days() <= 2 && p.currentStock() > 0)
                .sorted(Comparator.comparingLong(LowRotationDTO::exitsLast30Days))
                .limit(10)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<DemandPredictionDTO> demandPrediction() {
        LocalDateTime from = LocalDateTime.now().minusDays(30);
        Map<Product, Long> exits = movementRepository.findByMovementTypeAndCreatedAtAfter(MovementType.SALIDA, from).stream()
                .collect(Collectors.groupingBy(InventoryMovement::getProduct, Collectors.summingLong(InventoryMovement::getQuantity)));
        return productRepository.findAll().stream()
                .map(p -> {
                    double average = exits.getOrDefault(p, 0L) / 30.0;
                    return new DemandPredictionDTO(p.getId(), p.getName(), p.getSku(), Math.round(average * 100.0) / 100.0, Math.round(average * 30));
                })
                .sorted(Comparator.comparingLong(DemandPredictionDTO::projected30Days).reversed())
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ReplenishmentDTO> replenishment() {
        Map<Long, DemandPredictionDTO> predictions = demandPrediction().stream().collect(Collectors.toMap(DemandPredictionDTO::productId, p -> p));
        return productRepository.findAll().stream()
                .map(p -> {
                    DemandPredictionDTO prediction = predictions.get(p.getId());
                    int projected = prediction == null ? p.getMinimumStock() : (int) prediction.projected30Days();
                    int target = Math.max(p.getMinimumStock(), projected);
                    int suggested = Math.max(0, target - p.getCurrentStock());
                    return new ReplenishmentDTO(p.getId(), p.getName(), p.getSku(), p.getCurrentStock(), suggested,
                            suggested > 0 ? "Cubrir demanda estimada y stock minimo" : "Stock suficiente para el periodo");
                })
                .filter(r -> r.suggestedQuantity() > 0)
                .sorted(Comparator.comparingInt(ReplenishmentDTO::suggestedQuantity).reversed())
                .toList();
    }

    private List<MovementSerieDTO> movementSeries() {
        LocalDateTime from = LocalDateTime.now().minusDays(14);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");
        Map<String, List<InventoryMovement>> byDay = movementRepository.findByCreatedAtBetweenOrderByCreatedAtDesc(from, LocalDateTime.now()).stream()
                .collect(Collectors.groupingBy(m -> m.getCreatedAt().format(formatter)));
        return byDay.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> new MovementSerieDTO(
                        e.getKey(),
                        e.getValue().stream().filter(m -> m.getMovementType() == MovementType.ENTRADA || m.getMovementType() == MovementType.DEVOLUCION).mapToLong(InventoryMovement::getQuantity).sum(),
                        e.getValue().stream().filter(m -> m.getMovementType() == MovementType.SALIDA || m.getMovementType() == MovementType.MERMA).mapToLong(InventoryMovement::getQuantity).sum()
                ))
                .toList();
    }
}
