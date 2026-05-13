import { AlertTriangle, Boxes, MoveHorizontal, PackageX } from 'lucide-react'
import { analyticsApi } from '../../api/analyticsApi'
import StatCard from '../../components/cards/StatCard'
import MovementChart from '../../components/charts/MovementChart'
import TopProductsChart from '../../components/charts/TopProductsChart'
import StockChart from '../../components/charts/StockChart'
import Table from '../../components/common/Table'
import Spinner from '../../components/common/Spinner'
import { useFetch } from '../../hooks/useFetch'

export default function DashboardPage() {
  const { data, loading, error } = useFetch(analyticsApi.dashboard, [])
  if (loading) return <Spinner />
  if (error) return <p className="rounded-md bg-rose-50 p-3 text-rose-700">{error}</p>
  return (
    <section className="grid gap-6">
      <div>
        <h1 className="text-2xl font-bold text-slate-950">Dashboard</h1>
        <p className="text-sm text-slate-500">Indicadores operativos, alertas y recomendaciones.</p>
      </div>
      <div className="grid gap-4 sm:grid-cols-2 xl:grid-cols-5">
        <StatCard title="Productos" value={data.totalProducts} icon={Boxes} />
        <StatCard title="Stock bajo" value={data.lowStockProducts} icon={AlertTriangle} />
        <StatCard title="Sin stock" value={data.outOfStockProducts} icon={PackageX} />
        <StatCard title="Alertas activas" value={data.activeAlerts} icon={AlertTriangle} />
        <StatCard title="Movimientos mes" value={data.movementsThisMonth} icon={MoveHorizontal} />
      </div>
      <div className="grid gap-4 xl:grid-cols-3">
        <MovementChart data={data.movementSeries} />
        <TopProductsChart data={data.topProducts} />
        <StockChart total={data.totalProducts} low={data.lowStockProducts} out={data.outOfStockProducts} />
      </div>
      <div className="grid gap-4 xl:grid-cols-2">
        <Table columns={[{ key: 'productName', label: 'Baja rotacion' }, { key: 'exitsLast30Days', label: 'Salidas 30d' }, { key: 'currentStock', label: 'Stock' }]} rows={data.lowRotationProducts} />
        <Table columns={[{ key: 'productName', label: 'Reposicion' }, { key: 'currentStock', label: 'Stock' }, { key: 'suggestedQuantity', label: 'Sugerido' }]} rows={data.replenishmentRecommendations} />
      </div>
      <Table columns={[{ key: 'createdAt', label: 'Fecha', render: (r) => new Date(r.createdAt).toLocaleString() }, { key: 'productName', label: 'Producto' }, { key: 'movementType', label: 'Tipo' }, { key: 'quantity', label: 'Cantidad' }]} rows={data.recentMovements} />
    </section>
  )
}
