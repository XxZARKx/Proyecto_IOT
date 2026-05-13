package com.smartinventory.reports.controller;

import com.smartinventory.analytics.service.AnalyticsService;
import com.smartinventory.inventory.dto.InventoryMovementResponseDTO;
import com.smartinventory.inventory.service.InventoryService;
import com.smartinventory.product.dto.ProductResponseDTO;
import com.smartinventory.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @GetMapping(value = "/products.csv", produces = "text/csv")
    public ResponseEntity<String> productsCsv() {
        StringBuilder csv = new StringBuilder("sku,nombre,categoria,precio,stock_actual,stock_minimo,stock_maximo,estado\n");
        for (ProductResponseDTO product : productService.list(null)) {
            csv.append(escape(product.sku())).append(',')
                    .append(escape(product.name())).append(',')
                    .append(escape(product.categoryName())).append(',')
                    .append(product.unitPrice()).append(',')
                    .append(product.currentStock()).append(',')
                    .append(product.minimumStock()).append(',')
                    .append(product.maximumStock()).append(',')
                    .append(product.status()).append('\n');
        }
        return csv("reporte-productos.csv", csv.toString());
    }

    @GetMapping(value = "/movements.csv", produces = "text/csv")
    public ResponseEntity<String> movementsCsv() {
        StringBuilder csv = new StringBuilder("fecha,sku,producto,tipo,cantidad,stock_anterior,stock_nuevo,usuario,origen,motivo\n");
        for (InventoryMovementResponseDTO movement : inventoryService.list()) {
            csv.append(movement.createdAt()).append(',')
                    .append(escape(movement.sku())).append(',')
                    .append(escape(movement.productName())).append(',')
                    .append(movement.movementType()).append(',')
                    .append(movement.quantity()).append(',')
                    .append(movement.previousStock()).append(',')
                    .append(movement.newStock()).append(',')
                    .append(escape(movement.userName())).append(',')
                    .append(escape(movement.source())).append(',')
                    .append(escape(movement.reason())).append('\n');
        }
        return csv("reporte-movimientos.csv", csv.toString());
    }

    private ResponseEntity<String> csv(String filename, String content) {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .contentType(MediaType.parseMediaType("text/csv; charset=UTF-8"))
                .body(content);
    }

    private String escape(Object value) {
        if (value == null) {
            return "";
        }
        String text = value.toString().replace("\"", "\"\"");
        return "\"" + text + "\"";
    }
}
