package com.smartinventory.user.service;

import com.smartinventory.audit.service.AuditService;
import com.smartinventory.common.exception.BadRequestException;
import com.smartinventory.common.exception.ResourceNotFoundException;
import com.smartinventory.user.dto.UserRequestDTO;
import com.smartinventory.user.dto.UserResponseDTO;
import com.smartinventory.user.model.User;
import com.smartinventory.user.model.UserStatus;
import com.smartinventory.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuditService auditService;

    @Transactional(readOnly = true)
    public List<UserResponseDTO> list() {
        return userRepository.findAll().stream().map(this::toDto).toList();
    }

    @Transactional
    public UserResponseDTO create(UserRequestDTO dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new BadRequestException("El email ya esta registrado");
        }
        User user = User.builder()
                .fullName(dto.fullName())
                .email(dto.email())
                .password(passwordEncoder.encode(dto.password()))
                .role(dto.role())
                .status(UserStatus.ACTIVO)
                .build();
        User saved = userRepository.save(user);
        auditService.log("CREATE", "User", saved.getId(), "Usuario creado por administrador: " + saved.getEmail());
        return toDto(saved);
    }

    @Transactional(readOnly = true)
    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
    }

    public UserResponseDTO toDto(User user) {
        return new UserResponseDTO(user.getId(), user.getFullName(), user.getEmail(), user.getRole(), user.getStatus(), user.getCreatedAt());
    }
}
