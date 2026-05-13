# Arquitectura

```mermaid
flowchart LR
  React["React + Vite"] --> API["Spring Boot API REST"]
  API --> DB["PostgreSQL / Supabase"]
  Simulator["IoT Simulator Node.js"] --> API
  API --> Analytics["Analytics Services"]
```

El frontend consume endpoints REST protegidos por JWT. El backend concentra reglas de stock, seguridad, alertas e indicadores. Supabase actua como PostgreSQL administrado para Render.
