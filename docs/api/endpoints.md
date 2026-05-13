# Endpoints principales

| Modulo | Metodo | Ruta |
| --- | --- | --- |
| Auth | POST | `/api/auth/login` |
| Auth | POST | `/api/auth/register` |
| Auth | GET | `/api/auth/me` |
| Productos | GET/POST | `/api/products` |
| Productos | PUT | `/api/products/{id}` |
| Productos | PATCH | `/api/products/{id}/deactivate` |
| Productos | GET | `/api/products/low-stock` |
| Categorias | GET/POST | `/api/categories` |
| Inventario | POST | `/api/inventory/entry` |
| Inventario | POST | `/api/inventory/exit` |
| Inventario | POST | `/api/inventory/adjustment` |
| Inventario | GET | `/api/inventory/movements` |
| Alertas | GET | `/api/alerts/active` |
| Alertas | PATCH | `/api/alerts/{id}/resolve` |
| IoT | GET/POST | `/api/iot/devices` |
| IoT | GET/POST | `/api/iot/readings` |
| IoT | POST | `/api/iot/simulate` |
| Analytics | GET | `/api/analytics/dashboard` |
| Analytics | GET | `/api/analytics/top-products` |
| Analytics | GET | `/api/analytics/low-rotation` |
| Analytics | GET | `/api/analytics/demand-prediction` |
| Analytics | GET | `/api/analytics/replenishment-recommendations` |
| Reportes | GET | `/api/reports/summary` |
| Reportes | GET | `/api/reports/products.csv` |
| Reportes | GET | `/api/reports/movements.csv` |
| Auditoria | GET | `/api/audit` |

Swagger: `/swagger-ui.html`.
