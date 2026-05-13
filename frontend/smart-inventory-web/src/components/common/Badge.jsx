const palette = {
  PENDIENTE: 'bg-amber-100 text-amber-800',
  RESUELTA: 'bg-emerald-100 text-emerald-800',
  ACTIVO: 'bg-emerald-100 text-emerald-800',
  INACTIVO: 'bg-slate-100 text-slate-700',
  STOCK_CRITICO: 'bg-rose-100 text-rose-800',
  STOCK_BAJO: 'bg-amber-100 text-amber-800',
  SOBRESTOCK: 'bg-sky-100 text-sky-800',
}

export default function Badge({ children }) {
  return <span className={`inline-flex rounded px-2 py-1 text-xs font-semibold ${palette[children] || 'bg-slate-100 text-slate-700'}`}>{children}</span>
}
