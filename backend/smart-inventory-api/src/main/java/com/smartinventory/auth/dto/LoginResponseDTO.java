package com.smartinventory.auth.dto;

import com.smartinventory.user.dto.UserResponseDTO;

public record LoginResponseDTO(String token, String tokenType, UserResponseDTO user) {
}
