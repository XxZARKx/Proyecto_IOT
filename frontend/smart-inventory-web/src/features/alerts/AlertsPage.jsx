import { alertApi } from '../../api/alertApi'
import Badge from '../../components/common/Badge'
import Button from '../../components/common/Button'
import Table from '../../components/common/Table'
import { useFetch } from '../../hooks/useFetch'

export default function AlertsPage() {
  const { data, reload } = useFetch(alertApi.active, [])
  async function resolve(id) {
    await alertApi.resolve(id)
    reload()
  }
  return (
    <section className="grid gap-4">
      <h1 className="text-2xl font-bold text-slate-950">Alertas</h1>
      <Table rows={data || []} columns={[{ key: 'alertType', label: 'Tipo', render: (r) => <Badge>{r.alertType}</Badge> }, { key: 'productName', label: 'Producto' }, { key: 'message', label: 'Mensaje' }, { key: 'createdAt', label: 'Fecha', render: (r) => new Date(r.createdAt).toLocaleString() }, { key: 'id', label: 'Accion', render: (r) => <Button variant="secondary" onClick={() => resolve(r.id)}>Resolver</Button> }]} />
    </section>
  )
}
