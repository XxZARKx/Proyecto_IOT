import axios from 'axios'
import { API_URL } from '../app/config/env'

export const api = axios.create({
  baseURL: API_URL,
  headers: { 'Content-Type': 'application/json' },
})

api.interceptors.request.use((config) => {
  const token = localStorage.getItem('smart_inventory_token')
  if (token) config.headers.Authorization = `Bearer ${token}`
  return config
})
