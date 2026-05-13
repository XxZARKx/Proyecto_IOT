package com.smartinventory.config;

import com.smartinventory.alert.service.AlertService;
import com.smartinventory.category.model.Category;
import com.smartinventory.category.repository.CategoryRepository;
import com.smartinventory.inventory.model.InventoryMovement;
import com.smartinventory.inventory.model.MovementType;
import com.smartinventory.inventory.repository.InventoryMovementRepository;
import com.smartinventory.iot.model.DeviceStatus;
import com.smartinventory.iot.model.SensorDevice;
import com.smartinventory.iot.model.SensorReading;
import com.smartinventory.iot.model.SensorType;
import com.smartinventory.iot.repository.SensorDeviceRepository;
import com.smartinventory.iot.repository.SensorReadingRepository;
import com.smartinventory.product.model.Product;
import com.smartinventory.product.model.ProductStatus;
import com.smartinventory.product.repository.ProductRepository;
import com.smartinventory.user.model.Role;
import com.smartinventory.user.model.User;
import com.smartinventory.user.model.UserStatus;
import com.smartinventory.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DataSeeder {
    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner seedData(UserRepository users, CategoryRepository categories, ProductRepository products,
                               InventoryMovementRepository movements, SensorDeviceRepository devices,
                               SensorReadingRepository readings, AlertService alerts) {
        return args -> {
            if (users.count() == 0) {
                users.saveAll(List.of(
                        user("Administrador", "admin@smartinventory.local", Role.ADMIN),
                        user("Encargado Almacen", "almacen@smartinventory.local", Role.ENCARGADO_ALMACEN),
                        user("Gerencia", "gerente@smartinventory.local", Role.GERENTE)
                ));
            }
            if (categories.count() == 0) {
                categories.saveAll(List.of(
                        Category.builder().name("Abarrotes").description("Productos de alta rotacion").build(),
                        Category.builder().name("Bebidas").description("Bebidas embotelladas y cajas").build(),
                        Category.builder().name("Limpieza").description("Productos de limpieza del hogar").build()
                ));
            }
            if (products.count() == 0) {
                Category abarrotes = categories.findByActiveTrueOrderByNameAsc().get(0);
                Category bebidas = categories.findByActiveTrueOrderByNameAsc().get(1);
                Category limpieza = categories.findByActiveTrueOrderByNameAsc().get(2);
                products.saveAll(List.of(
                        product("Arroz superior 5kg", "PROD-0001", "775000001", new BigDecimal("18.90"), 8, 10, 90, abarrotes),
                        product("Aceite vegetal 1L", "PROD-0002", "775000002", new BigDecimal("9.50"), 0, 12, 80, abarrotes),
                        product("Agua mineral 625ml", "PROD-0003", "775000003", new BigDecimal("1.80"), 45, 20, 150, bebidas),
                        product("Gaseosa cola 3L", "PROD-0004", "775000004", new BigDecimal("11.20"), 22, 15, 120, bebidas),
                        product("Detergente 900g", "PROD-0005", "775000005", new BigDecimal("14.40"), 70, 8, 60, limpieza)
                ));
            }
            User admin = users.findByEmail("admin@smartinventory.local").orElse(null);
            if (movements.count() == 0) {
                Product p1 = products.findBySku("PROD-0001").orElseThrow();
                Product p2 = products.findBySku("PROD-0002").orElseThrow();
                Product p3 = products.findBySku("PROD-0003").orElseThrow();
                movements.saveAll(List.of(
                        movement(p1, MovementType.ENTRADA, 40, 0, 40, "Compra inicial", admin),
                        movement(p1, MovementType.SALIDA, 32, 40, 8, "Ventas de semana", admin),
                        movement(p2, MovementType.ENTRADA, 25, 0, 25, "Reposicion", admin),
                        movement(p2, MovementType.SALIDA, 25, 25, 0, "Ventas", admin),
                        movement(p3, MovementType.ENTRADA, 80, 0, 80, "Compra inicial", admin),
                        movement(p3, MovementType.SALIDA, 35, 80, 45, "Despacho a tienda", admin)
                ));
            }
            if (devices.count() == 0) {
                devices.saveAll(List.of(
                        SensorDevice.builder().code("RFID-ALM-001").name("Lector RFID almacen").sensorType(SensorType.RFID).location("Almacen principal").status(DeviceStatus.ACTIVO).build(),
                        SensorDevice.builder().code("PESO-EST-002").name("Sensor de peso estante").sensorType(SensorType.WEIGHT_SENSOR).location("Pasillo bebidas").status(DeviceStatus.ACTIVO).build()
                ));
            }
            if (readings.count() == 0) {
                SensorDevice device = devices.findByCode("RFID-ALM-001").orElseThrow();
                Product product = products.findBySku("PROD-0003").orElseThrow();
                readings.save(SensorReading.builder().sensorDevice(device).product(product).quantityDetected(5).rawValue("RFID:PROD-0003:5").location(device.getLocation()).movementType(MovementType.ENTRADA).processed(true).build());
            }
            products.findAll().forEach(alerts::evaluate);
        };
    }

    private User user(String name, String email, Role role) {
        return User.builder().fullName(name).email(email).password(passwordEncoder.encode("Password123")).role(role).status(UserStatus.ACTIVO).build();
    }

    private Product product(String name, String sku, String barcode, BigDecimal price, int current, int min, int max, Category category) {
        return Product.builder().name(name).description("Producto semilla para demo academica").sku(sku).barcode(barcode).unitPrice(price).currentStock(current).minimumStock(min).maximumStock(max).category(category).status(ProductStatus.ACTIVO).build();
    }

    private InventoryMovement movement(Product product, MovementType type, int qty, int previous, int next, String reason, User user) {
        return InventoryMovement.builder().product(product).movementType(type).quantity(qty).previousStock(previous).newStock(next).reason(reason).user(user).source("SEED").build();
    }
}
