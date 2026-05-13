import { config } from './src/config/apiConfig.js'
import { generateReading } from './src/generators/stockReadingGenerator.js'
import { login, sendReading } from './src/services/iotApiService.js'

async function tick(token) {
  const reading = generateReading()
  const result = await sendReading(token, reading)
  console.log(`[${new Date().toISOString()}] ${result.deviceCode} ${result.productSku} ${result.movementType} x${result.quantityDetected}`)
}

const loop = process.argv.includes('--loop')
const token = await login()
await tick(token)
if (loop) {
  setInterval(() => tick(token).catch((error) => console.error(error.response?.data || error.message)), config.intervalMs)
}
