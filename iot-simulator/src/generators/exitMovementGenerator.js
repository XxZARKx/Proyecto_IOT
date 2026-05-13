import { generateReading } from './stockReadingGenerator.js'

export function generateExit() {
  return { ...generateReading(), movementType: 'SALIDA' }
}
