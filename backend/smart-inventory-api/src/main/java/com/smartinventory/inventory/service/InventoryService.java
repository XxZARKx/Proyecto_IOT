package com.smartinventory.inventory.service;

import com.smartinventory.audit.service.AuditService;
import com.smartinventory.alert.service.AlertService;
import com.smartinventory.common.exception.BadRequestException;
import com.smartinventory.inventory.dto.InventoryMovementRequestDTO;
import com.smartinventory.inventory.dto.InventoryMovementResponseDTO;
import com.smartinventory.inventory.model.InventoryMovement;
import com.smartinventory.inventory.model.MovementType;
import com.smartinventory.inventory.repository.InventoryMovementRepository;
import com.smartinventory.product.model.Product;
import com.smartinventory.product.repository.ProductRepository;
import com.smartinventory.product.service.ProductService;
import com.smartinventory.user.model.User;
import com.smartinventory.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryMovementRepository movementRepository;
    private final ProductService productService;
    private final ProductRepository productRepository;
    private final UserService userService;
    private final AlertService alertService;
    private final AuditService auditService;

    @Transactional(readOnly = true)
    public List<InventoryMovementResponseDTO> list() {
        return movementRepository.findAll().stream().map(this::toDto).toList();
    }

    @Transactional(readOnly = true)
    public List<InventoryMovementResponseDTO> recent() {
        return movementRepository.findTop10ByOrderByCreatedAtDesc().stream().map(this::toDto).toList();
    }

    @Transactional(readOnly = true)
    public List<InventoryMovementResponseDTO> history(Long productId) {
        Product product = productService.get(productId);
        return movementRepository.findByProductOrderByCreatedAtDesc(product).stream().map(this::toDto).toList();
    }

    @Transactional
    public InventoryMovementResponseDTO register(InventoryMovementRequestDTO dto) {
        return toDto(registerMovement(productService.get(dto.productId()), dto.movementType(), dto.quantity(), dto.reason(), currentUser(), "WEB"));
    }

    @Transactional
    public InventoryMovement registerIotMovement(Product product, MovementType type, int quantity, String reason) {
        return registerMovement(product, type, quantity, reason, null, "IOT");
    }

    @Transactional(readOnly = true)
    public long countThisMonth() {
        LocalDateTime start = LocalDateTime.now().withDayOfMonth(1).toLocalDate().atStartOfDay();
        return movementRepository.findByCreatedAtBetweenOrderByCreatedAtDesc(start, LocalDateTime.now()).size();
    }

    public InventoryMovementResponseDTO toDto(InventoryMovement movement) {
        User user = movement.getUser();
        Product product = movement.getProduct();
        return new InventoryMovementResponseDTO(
                movement.getId(),
                product.getId(),
                product.getName(),
                product.getSku(),
                movement.getMovementType(),
                movement.getQuantity(),
                movement.getPreviousStock(),
                movement.getNewStock(),
                movement.getReason(),
                user == null ? "Sistema IoT" : user.getFullName(),
                movement.getSource(),
                movement.getCreatedAt());
    }

    private InventoryMovement registerMovement(Product product, MovementType type, int quantity, String reason, User user, String source) {
        int previous = product.getCurrentStock();
        int next = switch (type) {
            case ENTRADA, DEVOLUCION -> previous + quantity;
            case SALIDA, MERMA -> previous - quantity;
            case AJUSTE -> quantity;
        };
        if (next < 0) {
            throw new BadRequestException("Stock insuficiente para registrar la salida");
        }
        product.setCurrentStock(next);
        productRepository.save(product);
        InventoryMovement movement = movementRepository.save(InventoryMovement.builder()
                .product(product)
                .movementType(type)
                .quantity(quantity)
                .previousStock(previous)
                .newStock(next)
                .reason(reason)
                .user(user)
                .source(source)
                .build());
        alertService.evaluate(product);
        auditService.log("STOCK_" + type.name(), "InventoryMovement", movement.getId(),
                "Movimiento " + type + " de " + quantity + " unidades para " + product.getSku());
        return movement;
    }

    private User currentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.getByEmail(email);
    }
}
