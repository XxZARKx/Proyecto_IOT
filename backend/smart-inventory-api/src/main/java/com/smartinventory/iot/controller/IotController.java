package com.smartinventory.iot.controller;

import com.smartinventory.iot.dto.SensorDeviceRequestDTO;
import com.smartinventory.iot.dto.SensorReadingRequestDTO;
import com.smartinventory.iot.service.IotService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/iot")
@RequiredArgsConstructor
public class IotController {
    private final IotService iotService;

    @GetMapping("/devices")
    public Object devices() {
        return iotService.devices();
    }

    @PostMapping("/devices")
    public Object createDevice(@Valid @RequestBody SensorDeviceRequestDTO dto) {
        return iotService.createDevice(dto);
    }

    @GetMapping("/readings")
    public Object readings() {
        return iotService.readings();
    }

    @PostMapping("/readings")
    public Object registerReading(@Valid @RequestBody SensorReadingRequestDTO dto) {
        return iotService.registerReading(dto);
    }

    @PostMapping("/simulate")
    public Object simulate() {
        return iotService.simulate();
    }
}
