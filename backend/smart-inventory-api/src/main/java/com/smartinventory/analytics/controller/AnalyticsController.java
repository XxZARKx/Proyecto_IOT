package com.smartinventory.analytics.controller;

import com.smartinventory.analytics.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class AnalyticsController {
    private final AnalyticsService analyticsService;

    @GetMapping("/dashboard")
    public Object dashboard() {
        return analyticsService.dashboard();
    }

    @GetMapping("/top-products")
    public Object topProducts() {
        return analyticsService.topProducts();
    }

    @GetMapping("/low-rotation")
    public Object lowRotation() {
        return analyticsService.lowRotation();
    }

    @GetMapping("/demand-prediction")
    public Object demandPrediction() {
        return analyticsService.demandPrediction();
    }

    @GetMapping("/replenishment-recommendations")
    public Object replenishment() {
        return analyticsService.replenishment();
    }
}
