import devices from '../data/devices.json' with { type: 'json' }
import products from '../data/products.json' with { type: 'json' }

const movementTypes = ['ENTRADA', 'SALIDA', 'DEVOLUCION', 'MERMA']

export function generateReading() {
  const device = devices[Math.floor(Math.random() * devices.length)]
  const product = products[Math.floor(Math.random() * products.length)]
  const movementType = movementTypes[Math.floor(Math.random() * movementTypes.length)]
  return {
    deviceCode: device.code,
    productSku: product.sku,
    sensorType: device.sensorType,
    quantityDetected: Math.floor(Math.random() * 5) + 1,
    movementType,
    rawValue: `${device.sensorType}:${product.sku}:${Date.now()}`,
    location: device.location,
    processAsMovement: true
  }
}
