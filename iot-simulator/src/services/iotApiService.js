import axios from 'axios'
import { config } from '../config/apiConfig.js'

export async function login() {
  const { data } = await axios.post(`${config.apiUrl}/auth/login`, { email: config.email, password: config.password })
  return data.token
}

export async function sendReading(token, reading) {
  const { data } = await axios.post(`${config.apiUrl}/iot/readings`, reading, {
    headers: { Authorization: `Bearer ${token}` },
  })
  return data
}
