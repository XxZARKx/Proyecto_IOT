package com.smartinventory.alert.repository;

import com.smartinventory.alert.model.Alert;
import com.smartinventory.alert.model.AlertStatus;
import com.smartinventory.alert.model.AlertType;
import com.smartinventory.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlertRepository extends JpaRepository<Alert, Long> {
    List<Alert> findByStatusOrderByCreatedAtDesc(AlertStatus status);
    long countByStatus(AlertStatus status);
    boolean existsByProductAndAlertTypeAndStatus(Product product, AlertType alertType, AlertStatus status);
}
