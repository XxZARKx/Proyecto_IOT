import { api } from './axiosConfig'

export const auditApi = {
  recent: () => api.get('/audit').then((r) => r.data),
}
