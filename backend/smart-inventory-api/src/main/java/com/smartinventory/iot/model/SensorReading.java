package com.smartinventory.iot.model;

import com.smartinventory.inventory.model.MovementType;
import com.smartinventory.product.model.Product;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sensor_readings")
public class SensorReading {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sensor_device_id", nullable = false)
    private SensorDevice sensorDevice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer quantityDetected;

    private String rawValue;
    private String location;

    @Enumerated(EnumType.STRING)
    private MovementType movementType;

    private boolean processed;
    private LocalDateTime createdAt;

    @PrePersist
    void prePersist() {
        createdAt = LocalDateTime.now();
    }
}
