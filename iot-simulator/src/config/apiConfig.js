import 'dotenv/config'

export const config = {
  apiUrl: process.env.API_URL || 'http://localhost:8080/api',
  email: process.env.API_EMAIL || 'admin@smartinventory.local',
  password: process.env.API_PASSWORD || 'Password123',
  intervalMs: Number(process.env.INTERVAL_MS || 8000),
}
