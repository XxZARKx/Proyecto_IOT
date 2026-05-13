import { NavLink } from 'react-router-dom'
import { AlertTriangle, BarChart3, Boxes, ClipboardList, FileText, LayoutDashboard, PackagePlus, RadioTower, Tags, Users } from 'lucide-react'

const links = [
  { to: '/dashboard', label: 'Dashboard', icon: LayoutDashboard },
  { to: '/products', label: 'Productos', icon: Boxes },
  { to: '/categories', label: 'Categorias', icon: Tags },
  { to: '/inventory', label: 'Inventario', icon: PackagePlus },
  { to: '/alerts', label: 'Alertas', icon: AlertTriangle },
  { to: '/iot/devices', label: 'IoT', icon: RadioTower },
  { to: '/analytics', label: 'Analytics', icon: BarChart3 },
  { to: '/reports', label: 'Reportes', icon: FileText },
  { to: '/audit', label: 'Auditoria', icon: ClipboardList },
  { to: '/users', label: 'Usuarios', icon: Users },
]

export default function Sidebar() {
  return (
    <aside className="hidden w-64 shrink-0 border-r border-slate-200 bg-white lg:block">
      <div className="border-b border-slate-200 px-5 py-5">
        <p className="text-lg font-bold text-slate-950">StockIQ IoT</p>
        <p className="text-xs text-slate-500">Inventario inteligente</p>
      </div>
      <nav className="grid gap-1 p-3">
        {links.map(({ to, label, icon: Icon }) => (
          <NavLink key={to} to={to} className={({ isActive }) => `flex items-center gap-3 rounded-md px-3 py-2 text-sm font-medium ${isActive ? 'bg-teal-50 text-teal-800' : 'text-slate-600 hover:bg-slate-50'}`}>
            <Icon className="h-4 w-4" />
            {label}
          </NavLink>
        ))}
      </nav>
    </aside>
  )
}
