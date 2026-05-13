import MovementForm from './MovementForm'
import MovementHistoryPage from './MovementHistoryPage'

export default function InventoryPage({ type = 'ENTRADA' }) {
  return (
    <section className="grid gap-6">
      <div><h1 className="text-2xl font-bold text-slate-950">Inventario</h1><p className="text-sm text-slate-500">Entradas, salidas, ajustes e historial.</p></div>
      <MovementForm type={type} />
      <MovementHistoryPage compact />
    </section>
  )
}
