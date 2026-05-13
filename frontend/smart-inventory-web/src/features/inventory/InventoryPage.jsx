import MovementForm from './MovementForm'
import MovementHistoryPage from './MovementHistoryPage'
import { Link } from 'react-router-dom'
import Button from '../../components/common/Button'

export default function InventoryPage({ type = 'ENTRADA' }) {
  return (
    <section className="grid gap-6">
      <div><h1 className="text-2xl font-bold text-slate-950">Inventario</h1><p className="text-sm text-slate-500">Entradas, salidas, ajustes e historial.</p></div>
      <div className="flex flex-wrap gap-2">
        <Link to="/inventory/entry"><Button variant={type === 'ENTRADA' ? 'primary' : 'secondary'}>Entrada</Button></Link>
        <Link to="/inventory/exit"><Button variant={type === 'SALIDA' ? 'primary' : 'secondary'}>Salida</Button></Link>
        <Link to="/inventory/adjustment"><Button variant={type === 'AJUSTE' ? 'primary' : 'secondary'}>Ajuste</Button></Link>
        <Link to="/inventory/history"><Button variant="secondary">Historial</Button></Link>
      </div>
      <MovementForm type={type} />
      <MovementHistoryPage compact />
    </section>
  )
}
