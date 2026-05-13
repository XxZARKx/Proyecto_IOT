import { generateReading } from './stockReadingGenerator.js'

export function generateEntry() {
  return { ...generateReading(), movementType: 'ENTRADA' }
}
