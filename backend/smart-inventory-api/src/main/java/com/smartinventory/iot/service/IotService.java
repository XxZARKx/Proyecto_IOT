package com.smartinventory.iot.service;

import com.smartinventory.audit.service.AuditService;
import com.smartinventory.common.exception.BadRequestException;
import com.smartinventory.common.exception.ResourceNotFoundException;
import com.smartinventory.inventory.model.MovementType;
import com.smartinventory.inventory.service.InventoryService;
import com.smartinventory.iot.dto.SensorDeviceRequestDTO;
import com.smartinventory.iot.dto.SensorDeviceResponseDTO;
import com.smartinventory.iot.dto.SensorReadingRequestDTO;
import com.smartinventory.iot.dto.SensorReadingResponseDTO;
import com.smartinventory.iot.model.DeviceStatus;
import com.smartinventory.iot.model.SensorDevice;
import com.smartinventory.iot.model.SensorReading;
import com.smartinventory.iot.repository.SensorDeviceRepository;
import com.smartinventory.iot.repository.SensorReadingRepository;
import com.smartinventory.product.model.Product;
import com.smartinventory.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class IotService {
    private final SensorDeviceRepository deviceRepository;
    private final SensorReadingRepository readingRepository;
    private final ProductService productService;
    private final InventoryService inventoryService;
    private final AuditService auditService;
    private final Random random = new Random();

    @Transactional(readOnly = true)
    public List<SensorDeviceResponseDTO> devices() {
        return deviceRepository.findAll().stream().map(this::toDto).toList();
    }

    @Transactional
    public SensorDeviceResponseDTO createDevice(SensorDeviceRequestDTO dto) {
        if (deviceRepository.existsByCode(dto.code())) {
            throw new BadRequestException("El codigo del dispositivo ya existe");
        }
        SensorDevice device = SensorDevice.builder()
                .code(dto.code())
                .name(dto.name())
                .sensorType(dto.sensorType())
                .location(dto.location())
                .status(dto.status() == null ? DeviceStatus.ACTIVO : dto.status())
                .build();
        SensorDevice saved = deviceRepository.save(device);
        auditService.log("CREATE", "SensorDevice", saved.getId(), "Dispositivo IoT creado: " + saved.getCode());
        return toDto(saved);
    }

    @Transactional(readOnly = true)
    public List<SensorReadingResponseDTO> readings() {
        return readingRepository.findTop50ByOrderByCreatedAtDesc().stream().map(this::toDto).toList();
    }

    @Transactional
    public SensorReadingResponseDTO registerReading(SensorReadingRequestDTO dto) {
        SensorDevice device = deviceRepository.findByCode(dto.deviceCode())
                .orElseThrow(() -> new ResourceNotFoundException("Dispositivo IoT no encontrado"));
        Product product = productService.getBySku(dto.productSku());
        MovementType movementType = dto.movementType() == null ? MovementType.ENTRADA : dto.movementType();
        boolean process = dto.processAsMovement() == null || dto.processAsMovement();
        SensorReading reading = readingRepository.save(SensorReading.builder()
                .sensorDevice(device)
                .product(product)
                .quantityDetected(dto.quantityDetected())
                .rawValue(dto.rawValue() == null ? "SIM-" + random.nextInt(9999) : dto.rawValue())
                .location(dto.location() == null ? device.getLocation() : dto.location())
                .movementType(movementType)
                .processed(process)
                .build());
        if (process) {
            inventoryService.registerIotMovement(product, movementType, dto.quantityDetected(), "Lectura IoT " + device.getCode());
        }
        auditService.log("CREATE", "SensorReading", reading.getId(), "Lectura IoT " + device.getCode() + " para " + product.getSku());
        return toDto(reading);
    }

    @Transactional
    public SensorReadingResponseDTO simulate() {
        SensorDevice device = deviceRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Registra al menos un dispositivo IoT"));
        Product product = productService.list(null).stream().findFirst()
                .map(p -> productService.get(p.id()))
                .orElseThrow(() -> new ResourceNotFoundException("Registra al menos un producto"));
        MovementType type = random.nextBoolean() ? MovementType.ENTRADA : MovementType.SALIDA;
        return registerReading(new SensorReadingRequestDTO(device.getCode(), product.getSku(), device.getSensorType(), random.nextInt(5) + 1, type, null, device.getLocation(), true));
    }

    public SensorDeviceResponseDTO toDto(SensorDevice device) {
        return new SensorDeviceResponseDTO(device.getId(), device.getCode(), device.getName(), device.getSensorType(), device.getLocation(), device.getStatus(), device.getCreatedAt());
    }

    public SensorReadingResponseDTO toDto(SensorReading reading) {
        Product product = reading.getProduct();
        return new SensorReadingResponseDTO(reading.getId(), reading.getSensorDevice().getCode(), product.getSku(), product.getName(), reading.getQuantityDetected(), reading.getRawValue(), reading.getLocation(), reading.getMovementType(), reading.isProcessed(), reading.getCreatedAt());
    }
}
