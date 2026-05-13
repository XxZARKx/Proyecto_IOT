# Control Inteligente de Inventario mediante IoT y Big Data

Proyecto full stack academico-profesional para controlar inventario, movimientos, alertas, IoT simulado y analytics.

## Estructura

- `backend/smart-inventory-api`: Spring Boot, JWT, JPA, PostgreSQL/Supabase, Swagger.
- `frontend/smart-inventory-web`: React, Vite, Tailwind, Recharts.
- `iot-simulator`: generador Node.js de lecturas IoT.
- `database`: schema, seed y diccionario para Supabase/PostgreSQL.
- `analytics`: datasets y notas del modulo Big Data academico.
- `deployment`: Render, Vercel, Docker y Nginx.
- `docs`: documentacion tecnica, arquitectura y manuales.

## Ejecutar

Backend:

```bash
cd backend/smart-inventory-api
./mvnw spring-boot:run
```

Frontend:

```bash
cd frontend/smart-inventory-web
npm install
npm run dev
```

Simulador IoT:

```bash
cd iot-simulator
npm install
npm run simulate
```

## Credenciales demo

- `admin@smartinventory.local` / `Password123`
- `almacen@smartinventory.local` / `Password123`
- `gerente@smartinventory.local` / `Password123`

## Supabase

Usa la base PostgreSQL de Supabase con estas variables en Render:

- `DATABASE_URL=jdbc:postgresql://...supabase.../postgres?sslmode=require`
- `DATABASE_USERNAME=postgres.<project-ref>` si usas session pooler
- `DATABASE_PASSWORD=<password>`
- `JWT_SECRET=<cadena larga>`
- `CORS_ALLOWED_ORIGINS=https://tu-frontend.vercel.app`

Tambien puedes ejecutar `database/schema.sql` en el SQL Editor de Supabase si quieres crear tablas manualmente.
