package com.smartinventory.category.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequestDTO(@NotBlank String name, String description) {
}
