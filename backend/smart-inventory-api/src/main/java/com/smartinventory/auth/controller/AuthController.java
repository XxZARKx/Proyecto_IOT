package com.smartinventory.auth.controller;

import com.smartinventory.auth.dto.LoginRequestDTO;
import com.smartinventory.auth.dto.RegisterRequestDTO;
import com.smartinventory.auth.service.AuthService;
import com.smartinventory.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/login")
    public Object login(@Valid @RequestBody LoginRequestDTO dto) {
        return authService.login(dto);
    }

    @PostMapping("/register")
    public Object register(@Valid @RequestBody RegisterRequestDTO dto) {
        return authService.register(dto);
    }

    @GetMapping("/me")
    public Object me(Authentication authentication) {
        return userService.toDto(userService.getByEmail(authentication.getName()));
    }
}
