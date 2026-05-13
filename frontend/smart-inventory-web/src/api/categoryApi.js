import { api } from './axiosConfig'

export const categoryApi = {
  list: () => api.get('/categories').then((r) => r.data),
  create: (payload) => api.post('/categories', payload).then((r) => r.data),
}
