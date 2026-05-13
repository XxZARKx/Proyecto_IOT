import { analyticsApi } from '../../api/analyticsApi'
import Table from '../../components/common/Table'
import { useFetch } from '../../hooks/useFetch'

export default function AnalyticsPage() {
  const top = useFetch(analyticsApi.topProducts, [])
  const low = useFetch(analyticsApi.lowRotation, [])
  const rep = useFetch(analyticsApi.replenishment, [])
  return (
    <section className="grid gap-5">
      <h1 className="text-2xl font-bold text-slate-950">Analytics</h1>
      <Table rows={top.data || []} columns={[{ key: 'productName', label: 'Mas vendidos' }, { key: 'sku', label: 'SKU' }, { key: 'totalOut', label: 'Salidas' }]} />
      <Table rows={low.data || []} columns={[{ key: 'productName', label: 'Baja rotacion' }, { key: 'sku', label: 'SKU' }, { key: 'exitsLast30Days', label: 'Salidas 30d' }, { key: 'currentStock', label: 'Stock' }]} />
      <Table rows={rep.data || []} columns={[{ key: 'productName', label: 'Reposicion sugerida' }, { key: 'currentStock', label: 'Stock' }, { key: 'suggestedQuantity', label: 'Comprar' }, { key: 'reason', label: 'Motivo' }]} />
    </section>
  )
}
