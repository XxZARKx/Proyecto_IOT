package com.smartinventory.alert.service;

import com.smartinventory.audit.service.AuditService;
import com.smartinventory.alert.dto.AlertResponseDTO;
import com.smartinventory.alert.model.Alert;
import com.smartinventory.alert.model.AlertStatus;
import com.smartinventory.alert.model.AlertType;
import com.smartinventory.alert.repository.AlertRepository;
import com.smartinventory.common.exception.ResourceNotFoundException;
import com.smartinventory.common.util.StockUtils;
import com.smartinventory.product.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlertService {
    private final AlertRepository alertRepository;
    private final AuditService auditService;

    @Transactional(readOnly = true)
    public List<AlertResponseDTO> active() {
        return alertRepository.findByStatusOrderByCreatedAtDesc(AlertStatus.PENDIENTE).stream().map(this::toDto).toList();
    }

    @Transactional
    public AlertResponseDTO resolve(Long id) {
        Alert alert = alertRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Alerta no encontrada"));
        alert.setStatus(AlertStatus.RESUELTA);
        alert.setResolvedAt(LocalDateTime.now());
        Alert saved = alertRepository.save(alert);
        auditService.log("RESOLVE", "Alert", saved.getId(), "Alerta resuelta: " + saved.getAlertType());
        return toDto(saved);
    }

    @Transactional
    public void evaluate(Product product) {
        if (StockUtils.isCritical(product.getCurrentStock())) {
            createIfMissing(product, AlertType.STOCK_CRITICO, "Producto sin stock: " + product.getName());
            return;
        }
        if (StockUtils.isLowStock(product.getCurrentStock(), product.getMinimumStock())) {
            createIfMissing(product, AlertType.STOCK_BAJO, "Stock bajo para " + product.getName() + ". Stock actual: " + product.getCurrentStock());
        }
        if (StockUtils.isOverstock(product.getCurrentStock(), product.getMaximumStock())) {
            createIfMissing(product, AlertType.SOBRESTOCK, "Sobrestock detectado para " + product.getName());
        }
    }

    @Transactional(readOnly = true)
    public long countActive() {
        return alertRepository.countByStatus(AlertStatus.PENDIENTE);
    }

    public AlertResponseDTO toDto(Alert alert) {
        Product product = alert.getProduct();
        return new AlertResponseDTO(
                alert.getId(),
                product == null ? null : product.getId(),
                product == null ? null : product.getName(),
                alert.getAlertType(),
                alert.getMessage(),
                alert.getStatus(),
                alert.getCreatedAt(),
                alert.getResolvedAt());
    }

    private void createIfMissing(Product product, AlertType type, String message) {
        boolean exists = alertRepository.existsByProductAndAlertTypeAndStatus(product, type, AlertStatus.PENDIENTE);
        if (!exists) {
            alertRepository.save(Alert.builder().product(product).alertType(type).message(message).status(AlertStatus.PENDIENTE).build());
        }
    }
}
