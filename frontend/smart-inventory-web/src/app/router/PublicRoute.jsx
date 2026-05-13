import { Navigate, Outlet } from 'react-router-dom'
import { useAuth } from '../providers/AuthProvider'

export default function PublicRoute() {
  const { isAuthenticated } = useAuth()
  return isAuthenticated ? <Navigate to="/dashboard" replace /> : <Outlet />
}
