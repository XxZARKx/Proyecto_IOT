import { analyticsApi } from '../../api/analyticsApi'
import { inventoryApi } from '../../api/inventoryApi'
import { productApi } from '../../api/productApi'
import StatCard from '../../components/cards/StatCard'
import Table from '../../components/common/Table'
import { useFetch } from '../../hooks/useFetch'

export default function ReportsPage() {
  const products = useFetch(() => productApi.list(), [])
  const movements = useFetch(inventoryApi.movements, [])
  const replenishment = useFetch(analyticsApi.replenishment, [])
  return (
    <section className="grid gap-5">
      <h1 className="text-2xl font-bold text-slate-950">Reportes</h1>
      <div className="grid gap-4 md:grid-cols-3">
        <StatCard title="Productos registrados" value={products.data?.length || 0} />
        <StatCard title="Movimientos historicos" value={movements.data?.length || 0} />
        <StatCard title="Reposiciones sugeridas" value={replenishment.data?.length || 0} />
      </div>
      <Table rows={movements.data || []} columns={[{ key: 'createdAt', label: 'Fecha', render: (r) => new Date(r.createdAt).toLocaleString() }, { key: 'productName', label: 'Producto' }, { key: 'movementType', label: 'Tipo' }, { key: 'quantity', label: 'Cantidad' }]} />
    </section>
  )
}
