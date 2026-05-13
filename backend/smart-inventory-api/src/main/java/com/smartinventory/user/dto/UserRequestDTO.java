package com.smartinventory.user.dto;

import com.smartinventory.user.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(
        @NotBlank String fullName,
        @Email @NotBlank String email,
        @Size(min = 6) String password,
        @NotNull Role role
) {
}
