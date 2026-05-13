package com.smartinventory.auth.service;

import com.smartinventory.auth.dto.LoginRequestDTO;
import com.smartinventory.auth.dto.LoginResponseDTO;
import com.smartinventory.auth.dto.RegisterRequestDTO;
import com.smartinventory.common.exception.BadRequestException;
import com.smartinventory.security.JwtService;
import com.smartinventory.user.dto.UserResponseDTO;
import com.smartinventory.user.model.Role;
import com.smartinventory.user.model.User;
import com.smartinventory.user.model.UserStatus;
import com.smartinventory.user.repository.UserRepository;
import com.smartinventory.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public LoginResponseDTO login(LoginRequestDTO dto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.email(), dto.password()));
        User user = userService.getByEmail(dto.email());
        String token = jwtService.generateToken(userDetailsService.loadUserByUsername(dto.email()), user.getRole().name());
        return new LoginResponseDTO(token, "Bearer", userService.toDto(user));
    }

    public UserResponseDTO register(RegisterRequestDTO dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new BadRequestException("El email ya esta registrado");
        }
        User user = User.builder()
                .fullName(dto.fullName())
                .email(dto.email())
                .password(passwordEncoder.encode(dto.password()))
                .role(dto.role() == null ? Role.ENCARGADO_ALMACEN : dto.role())
                .status(UserStatus.ACTIVO)
                .build();
        return userService.toDto(userRepository.save(user));
    }
}
