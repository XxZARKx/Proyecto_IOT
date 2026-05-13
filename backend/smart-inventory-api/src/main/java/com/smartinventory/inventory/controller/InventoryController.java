package com.smartinventory.inventory.controller;

import com.smartinventory.inventory.dto.InventoryMovementRequestDTO;
import com.smartinventory.inventory.model.MovementType;
import com.smartinventory.inventory.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping("/movements")
    public Object movements() {
        return inventoryService.list();
    }

    @PostMapping("/entry")
    public Object entry(@Valid @RequestBody InventoryMovementRequestDTO dto) {
        return inventoryService.register(new InventoryMovementRequestDTO(dto.productId(), MovementType.ENTRADA, dto.quantity(), dto.reason()));
    }

    @PostMapping("/exit")
    public Object exit(@Valid @RequestBody InventoryMovementRequestDTO dto) {
        return inventoryService.register(new InventoryMovementRequestDTO(dto.productId(), MovementType.SALIDA, dto.quantity(), dto.reason()));
    }

    @PostMapping("/adjustment")
    public Object adjustment(@Valid @RequestBody InventoryMovementRequestDTO dto) {
        return inventoryService.register(new InventoryMovementRequestDTO(dto.productId(), MovementType.AJUSTE, dto.quantity(), dto.reason()));
    }

    @PostMapping("/movement")
    public Object movement(@Valid @RequestBody InventoryMovementRequestDTO dto) {
        return inventoryService.register(dto);
    }

    @GetMapping("/product/{id}/history")
    public Object history(@PathVariable Long id) {
        return inventoryService.history(id);
    }
}
