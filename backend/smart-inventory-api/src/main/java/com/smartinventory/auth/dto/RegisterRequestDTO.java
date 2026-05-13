package com.smartinventory.auth.dto;

import com.smartinventory.user.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequestDTO(
        @NotBlank String fullName,
        @Email @NotBlank String email,
        @Size(min = 6) String password,
        Role role
) {
}
