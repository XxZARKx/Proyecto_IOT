package com.smartinventory.alert.controller;

import com.smartinventory.alert.service.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/alerts")
@RequiredArgsConstructor
public class AlertController {
    private final AlertService alertService;

    @GetMapping("/active")
    public Object active() {
        return alertService.active();
    }

    @PatchMapping("/{id}/resolve")
    public Object resolve(@PathVariable Long id) {
        return alertService.resolve(id);
    }
}
