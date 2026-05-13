# Segunda version de StockIQ IoT

## Objetivo

Mejorar la seguridad, trazabilidad, reporteria y experiencia de uso del sistema sin cambiar la arquitectura base.

## Cambios principales

- Se corrigio la configuracion de seguridad para que `/api/auth/me` requiera JWT.
- El registro publico ya no puede crear usuarios con rol ADMIN o GERENTE.
- Se agrego auditoria funcional con endpoint `/api/audit`.
- Se registran eventos de productos, categorias, inventario, alertas, usuarios e IoT.
- Se agrego desactivacion de productos con `/api/products/{id}/deactivate`.
- Se agregaron exportaciones CSV para productos y movimientos.
- Se agrego pantalla de Auditoria en el frontend.
- Se agregaron botones de descarga CSV en Reportes.
- Se mejoraron acciones de productos: ver, editar y desactivar.
- Se agrego acceso visual a entrada, salida, ajuste e historial de inventario.
- Se agrego limpieza de sesion local ante respuestas 401.

## Verificacion

- Backend: `./mvnw.cmd -q -DskipTests package`
- Frontend: `npm run build`

Ambas compilaciones finalizaron correctamente.
