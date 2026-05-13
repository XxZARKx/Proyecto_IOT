package com.smartinventory.category.dto;

import java.time.LocalDateTime;

public record CategoryResponseDTO(Long id, String name, String description, boolean active, LocalDateTime createdAt) {
}
