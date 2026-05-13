import { api } from './axiosConfig'

export const iotApi = {
  devices: () => api.get('/iot/devices').then((r) => r.data),
  createDevice: (payload) => api.post('/iot/devices', payload).then((r) => r.data),
  readings: () => api.get('/iot/readings').then((r) => r.data),
  createReading: (payload) => api.post('/iot/readings', payload).then((r) => r.data),
  simulate: () => api.post('/iot/simulate').then((r) => r.data),
}
