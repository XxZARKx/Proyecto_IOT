import { api } from './axiosConfig'

export const analyticsApi = {
  dashboard: () => api.get('/analytics/dashboard').then((r) => r.data),
  topProducts: () => api.get('/analytics/top-products').then((r) => r.data),
  lowRotation: () => api.get('/analytics/low-rotation').then((r) => r.data),
  prediction: () => api.get('/analytics/demand-prediction').then((r) => r.data),
  replenishment: () => api.get('/analytics/replenishment-recommendations').then((r) => r.data),
}
