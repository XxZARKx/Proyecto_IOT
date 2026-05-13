# Diccionario de datos

| Tabla | Uso |
| --- | --- |
| `users` | Usuarios con roles ADMIN, ENCARGADO_ALMACEN, GERENTE y SISTEMA_IOT. |
| `categories` | Agrupacion comercial de productos. |
| `products` | SKU, precio, stock actual, minimo y maximo. |
| `inventory_movements` | Trazabilidad de entradas, salidas, ajustes, devoluciones y mermas. |
| `alerts` | Stock bajo, critico, sobrestock y recomendaciones. |
| `sensor_devices` | Dispositivos IoT virtuales. |
| `sensor_readings` | Lecturas simuladas que pueden procesarse como movimientos. |
| `audit_logs` | Registro de acciones relevantes. |

Supabase usa PostgreSQL, por lo que `schema.sql` puede ejecutarse en el SQL Editor si prefieres controlar el esquema manualmente. En desarrollo, Hibernate puede crear/actualizar tablas con `JPA_DDL_AUTO=update`.
