import Button from '../common/Button'
import { useAuth } from '../../app/providers/AuthProvider'

export default function Navbar() {
  const { user, logout } = useAuth()
  return (
    <header className="flex min-h-16 items-center justify-between border-b border-slate-200 bg-white px-4 lg:px-8">
      <div>
        <p className="text-sm font-semibold text-slate-900">{user?.fullName}</p>
        <p className="text-xs text-slate-500">{user?.role}</p>
      </div>
      <Button variant="secondary" onClick={logout}>Cerrar sesion</Button>
    </header>
  )
}
