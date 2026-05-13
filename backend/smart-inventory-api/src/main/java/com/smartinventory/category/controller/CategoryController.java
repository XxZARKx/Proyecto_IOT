package com.smartinventory.category.controller;

import com.smartinventory.category.dto.CategoryRequestDTO;
import com.smartinventory.category.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public Object list() {
        return categoryService.list();
    }

    @PostMapping
    public Object create(@Valid @RequestBody CategoryRequestDTO dto) {
        return categoryService.create(dto);
    }
}
