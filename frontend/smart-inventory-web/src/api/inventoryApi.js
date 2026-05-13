import { api } from './axiosConfig'

export const inventoryApi = {
  movements: () => api.get('/inventory/movements').then((r) => r.data),
  entry: (payload) => api.post('/inventory/entry', payload).then((r) => r.data),
  exit: (payload) => api.post('/inventory/exit', payload).then((r) => r.data),
  adjustment: (payload) => api.post('/inventory/adjustment', payload).then((r) => r.data),
  history: (id) => api.get(`/inventory/product/${id}/history`).then((r) => r.data),
}
