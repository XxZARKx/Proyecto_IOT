import { inventoryApi } from '../../api/inventoryApi'
import Table from '../../components/common/Table'
import { useFetch } from '../../hooks/useFetch'

export default function MovementHistoryPage({ compact = false }) {
  const { data } = useFetch(inventoryApi.movements, [])
  return (
    <section className="grid gap-4">
      {!compact && <h1 className="text-2xl font-bold text-slate-950">Historial de movimientos</h1>}
      <Table rows={data || []} columns={[{ key: 'createdAt', label: 'Fecha', render: (r) => new Date(r.createdAt).toLocaleString() }, { key: 'productName', label: 'Producto' }, { key: 'movementType', label: 'Tipo' }, { key: 'quantity', label: 'Cantidad' }, { key: 'previousStock', label: 'Antes' }, { key: 'newStock', label: 'Despues' }, { key: 'userName', label: 'Usuario' }]} />
    </section>
  )
}
