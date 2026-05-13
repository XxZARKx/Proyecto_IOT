package com.smartinventory.reports.controller;

import com.smartinventory.analytics.service.AnalyticsService;
import com.smartinventory.inventory.service.InventoryService;
import com.smartinventory.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportsController {
    private final ProductService productService;
    private final InventoryService inventoryService;
    private final AnalyticsService analyticsService;

    @GetMapping("/summary")
    public Object summary() {
        return Map.of(
                "products", productService.list(null),
                "movements", inventoryService.list(),
                "replenishment", analyticsService.replenishment()
        );
    }
}
