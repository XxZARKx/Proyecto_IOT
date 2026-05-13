import { analyticsApi } from '../../api/analyticsApi'
import Table from '../../components/common/Table'
import { useFetch } from '../../hooks/useFetch'

export default function DemandPredictionPage() {
  const { data } = useFetch(analyticsApi.prediction, [])
  return (
    <section className="grid gap-4">
      <h1 className="text-2xl font-bold text-slate-950">Prediccion de demanda</h1>
      <Table rows={data || []} columns={[{ key: 'productName', label: 'Producto' }, { key: 'sku', label: 'SKU' }, { key: 'averageDailyDemand', label: 'Demanda diaria' }, { key: 'projected30Days', label: 'Proyeccion 30d' }]} />
    </section>
  )
}
