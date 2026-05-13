# Guia de uso de StockIQ IoT

## 1. Acceso a la aplicacion

URL de la aplicacion desplegada:

`https://proyecto-iot-murex.vercel.app/login`

Credenciales de demostracion:

- Administrador: `admin@smartinventory.local` / `Password123`
- Encargado de almacen: `almacen@smartinventory.local` / `Password123`
- Gerente: `gerente@smartinventory.local` / `Password123`

## 2. Dashboard

Al iniciar sesion, el sistema muestra un panel con indicadores principales:

- Total de productos registrados.
- Productos con stock bajo.
- Productos sin stock.
- Alertas activas.
- Movimientos registrados durante el mes.
- Grafico de entradas vs salidas.
- Productos mas vendidos.
- Productos con baja rotacion.
- Recomendaciones de reposicion.
- Movimientos recientes.

## 3. Productos

Desde el menu Productos se puede:

- Buscar productos por nombre o SKU.
- Revisar stock actual, stock minimo y estado.
- Crear productos nuevos.
- Consultar detalle e historial de movimientos.

Para crear un producto, ingresa a Productos > Nuevo producto y completa nombre, SKU, precio, stock actual, stock minimo, stock maximo y categoria.

## 4. Categorias

El modulo Categorias permite organizar los productos por lineas comerciales. Para crear una categoria, ingresa nombre y descripcion, y pulsa Crear.

## 5. Inventario

El modulo Inventario permite registrar:

- Entradas: aumentan el stock.
- Salidas: disminuyen el stock si hay disponibilidad.
- Ajustes: fijan un nuevo valor de stock.

Cada movimiento guarda producto, cantidad, stock anterior, stock nuevo, usuario, fecha y origen.

## 6. Alertas

El sistema genera alertas automaticamente cuando:

- El stock actual es menor o igual al stock minimo.
- El producto queda sin stock.
- El stock supera el maximo configurado.

Desde Alertas se pueden revisar y resolver alertas activas.

## 7. IoT simulado

El modulo IoT permite representar sensores virtuales como RFID, QR, codigo de barras, sensor de peso o sensor de estante.

Funciones disponibles:

- Registrar dispositivos IoT simulados.
- Ver lecturas recibidas.
- Generar lecturas desde el simulador.
- Procesar lecturas como movimientos de inventario.

## 8. Analytics

Analytics transforma el historial en indicadores:

- Productos mas vendidos.
- Productos con baja rotacion.
- Prediccion simple de demanda.
- Recomendaciones de reposicion.

Estos calculos se basan en movimientos historicos y stock actual.

## 9. Reportes

Reportes resume productos registrados, movimientos historicos y recomendaciones de reposicion para una revision rapida.

## 10. Buenas practicas de uso

- Registra categorias antes de crear productos.
- Configura correctamente stock minimo y maximo.
- Usa entradas para compras o reposiciones.
- Usa salidas para ventas, despachos o consumo.
- Revisa alertas antes de realizar nuevas compras.
- Usa Analytics para priorizar productos criticos.
- Cambia las credenciales demo antes de una presentacion publica.
