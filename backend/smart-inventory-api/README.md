# Smart Inventory API

API REST Spring Boot para control inteligente de inventario con JWT, PostgreSQL/Supabase, IoT simulado y analytics.

## Ejecutar local

1. Crea una base PostgreSQL `smart_inventory` o usa `docker compose up`.
2. Copia `.env.example` a `.env` si quieres usar variables reales.
3. Ejecuta:

```bash
./mvnw spring-boot:run
```

Swagger queda en `http://localhost:8080/swagger-ui.html`.

## Usuarios semilla

- `admin@smartinventory.local` / `Password123`
- `almacen@smartinventory.local` / `Password123`
- `gerente@smartinventory.local` / `Password123`

## Render + Supabase

En Render configura:

- Build command: `./mvnw -DskipTests package`
- Start command: `java -jar target/smart-inventory-api-0.0.1-SNAPSHOT.jar`
- Variables: `DATABASE_URL`, `DATABASE_USERNAME`, `DATABASE_PASSWORD`, `JWT_SECRET`, `CORS_ALLOWED_ORIGINS`, `SPRING_PROFILES_ACTIVE=prod`.
