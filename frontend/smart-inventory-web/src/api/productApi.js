import { api } from './axiosConfig'

export const productApi = {
  list: (search = '') => api.get('/products', { params: { search } }).then((r) => r.data),
  create: (payload) => api.post('/products', payload).then((r) => r.data),
  update: (id, payload) => api.put(`/products/${id}`, payload).then((r) => r.data),
  lowStock: () => api.get('/products/low-stock').then((r) => r.data),
}
