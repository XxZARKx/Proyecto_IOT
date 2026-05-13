package com.smartinventory.inventory.repository;

import com.smartinventory.inventory.model.InventoryMovement;
import com.smartinventory.inventory.model.MovementType;
import com.smartinventory.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface InventoryMovementRepository extends JpaRepository<InventoryMovement, Long> {
    List<InventoryMovement> findTop10ByOrderByCreatedAtDesc();
    List<InventoryMovement> findByProductOrderByCreatedAtDesc(Product product);
    List<InventoryMovement> findByCreatedAtBetweenOrderByCreatedAtDesc(LocalDateTime from, LocalDateTime to);
    List<InventoryMovement> findByMovementTypeAndCreatedAtAfter(MovementType movementType, LocalDateTime from);
}
