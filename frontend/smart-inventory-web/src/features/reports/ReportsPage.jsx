import { analyticsApi } from '../../api/analyticsApi'
import { inventoryApi } from '../../api/inventoryApi'
import { productApi } from '../../api/productApi'
import { reportApi } from '../../api/reportApi'
import StatCard from '../../components/cards/StatCard'
import Button from '../../components/common/Button'
import Table from '../../components/common/Table'
import { useFetch } from '../../hooks/useFetch'

export default function ReportsPage() {
  const products = useFetch(() => productApi.list(), [])
  const movements = useFetch(inventoryApi.movements, [])
  const replenishment = useFetch(analyticsApi.replenishment, [])
  return (
    <section className="grid gap-5">
      <div className="flex flex-wrap items-end justify-between gap-3">
        <div>
          <h1 className="text-2xl font-bold text-slate-950">Reportes</h1>
          <p className="text-sm text-slate-500">Resumen operativo y exportaciones descargables.</p>
        </div>
        <div className="flex flex-wrap gap-2">
          <Button type="button" variant="secondary" onClick={() => reportApi.downloadCsv('/reports/products.csv', 'reporte-productos.csv')}>Productos CSV</Button>
          <Button type="button" variant="secondary" onClick={() => reportApi.downloadCsv('/reports/movements.csv', 'reporte-movimientos.csv')}>Movimientos CSV</Button>
        </div>
      </div>
      <div className="grid gap-4 md:grid-cols-3">
        <StatCard title="Productos registrados" value={products.data?.length || 0} />
        <StatCard title="Movimientos historicos" value={movements.data?.length || 0} />
        <StatCard title="Reposiciones sugeridas" value={replenishment.data?.length || 0} />
      </div>
      <Table rows={movements.data || []} columns={[{ key: 'createdAt', label: 'Fecha', render: (r) => new Date(r.createdAt).toLocaleString() }, { key: 'productName', label: 'Producto' }, { key: 'movementType', label: 'Tipo' }, { key: 'quantity', label: 'Cantidad' }]} />
    </section>
  )
}
