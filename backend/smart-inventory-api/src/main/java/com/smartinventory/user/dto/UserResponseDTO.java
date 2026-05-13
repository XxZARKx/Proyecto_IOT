package com.smartinventory.user.dto;

import com.smartinventory.user.model.Role;
import com.smartinventory.user.model.UserStatus;

import java.time.LocalDateTime;

public record UserResponseDTO(Long id, String fullName, String email, Role role, UserStatus status, LocalDateTime createdAt) {
}
