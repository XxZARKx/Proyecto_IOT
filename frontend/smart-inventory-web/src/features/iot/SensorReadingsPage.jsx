import { iotApi } from '../../api/iotApi'
import Table from '../../components/common/Table'
import { useFetch } from '../../hooks/useFetch'

export default function SensorReadingsPage() {
  const { data } = useFetch(iotApi.readings, [])
  return (
    <section className="grid gap-4">
      <h1 className="text-2xl font-bold text-slate-950">Lecturas IoT</h1>
      <Table rows={data || []} columns={[{ key: 'createdAt', label: 'Fecha', render: (r) => new Date(r.createdAt).toLocaleString() }, { key: 'deviceCode', label: 'Dispositivo' }, { key: 'productName', label: 'Producto' }, { key: 'quantityDetected', label: 'Cantidad' }, { key: 'movementType', label: 'Movimiento' }, { key: 'processed', label: 'Procesada', render: (r) => r.processed ? 'Si' : 'No' }]} />
    </section>
  )
}
