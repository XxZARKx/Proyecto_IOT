import { api } from './axiosConfig'

export const alertApi = {
  active: () => api.get('/alerts/active').then((r) => r.data),
  resolve: (id) => api.patch(`/alerts/${id}/resolve`).then((r) => r.data),
}
