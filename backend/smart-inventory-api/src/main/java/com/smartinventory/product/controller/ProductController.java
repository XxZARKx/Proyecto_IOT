package com.smartinventory.product.controller;

import com.smartinventory.product.dto.ProductRequestDTO;
import com.smartinventory.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public Object list(@RequestParam(required = false) String search) {
        return productService.list(search);
    }

    @PostMapping
    public Object create(@Valid @RequestBody ProductRequestDTO dto) {
        return productService.create(dto);
    }

    @PutMapping("/{id}")
    public Object update(@PathVariable Long id, @Valid @RequestBody ProductRequestDTO dto) {
        return productService.update(id, dto);
    }

    @PatchMapping("/{id}/deactivate")
    public Object deactivate(@PathVariable Long id) {
        return productService.deactivate(id);
    }

    @GetMapping("/low-stock")
    public Object lowStock() {
        return productService.lowStock();
    }
}
