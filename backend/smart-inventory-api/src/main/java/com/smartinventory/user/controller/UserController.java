package com.smartinventory.user.controller;

import com.smartinventory.user.dto.UserRequestDTO;
import com.smartinventory.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Object list() {
        return userService.list();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Object create(@Valid @RequestBody UserRequestDTO dto) {
        return userService.create(dto);
    }
}
