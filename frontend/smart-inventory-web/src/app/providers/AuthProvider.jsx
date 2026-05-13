import { createContext, useContext, useEffect, useMemo, useState } from 'react'
import { authApi } from '../../api/authApi'

const AuthContext = createContext(null)

export function AuthProvider({ children }) {
  const [user, setUser] = useState(() => JSON.parse(localStorage.getItem('smart_inventory_user') || 'null'))
  const [loading, setLoading] = useState(false)

  useEffect(() => {
    const token = localStorage.getItem('smart_inventory_token')
    if (!token) return
    authApi.me().then(setUser).catch(() => logout())
  }, [])

  async function login(credentials) {
    setLoading(true)
    try {
      const data = await authApi.login(credentials)
      localStorage.setItem('smart_inventory_token', data.token)
      localStorage.setItem('smart_inventory_user', JSON.stringify(data.user))
      setUser(data.user)
      return data.user
    } finally {
      setLoading(false)
    }
  }

  function logout() {
    localStorage.removeItem('smart_inventory_token')
    localStorage.removeItem('smart_inventory_user')
    setUser(null)
  }

  const value = useMemo(() => ({ user, loading, login, logout, isAuthenticated: Boolean(user) }), [user, loading])
  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>
}

export const useAuth = () => useContext(AuthContext)
