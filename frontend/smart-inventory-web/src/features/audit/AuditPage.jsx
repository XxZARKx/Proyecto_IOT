import { auditApi } from '../../api/auditApi'
import Table from '../../components/common/Table'
import { useFetch } from '../../hooks/useFetch'

export default function AuditPage() {
  const { data, error } = useFetch(auditApi.recent, [])
  return (
    <section className="grid gap-4">
      <div>
        <h1 className="text-2xl font-bold text-slate-950">Auditoria</h1>
        <p className="text-sm text-slate-500">Ultimas acciones registradas en productos, inventario, alertas, usuarios e IoT.</p>
      </div>
      {error && <p className="rounded-md bg-amber-50 p-3 text-sm text-amber-800">Solo ADMIN y GERENTE pueden consultar auditoria.</p>}
      <Table rows={data || []} columns={[
        { key: 'createdAt', label: 'Fecha', render: (r) => new Date(r.createdAt).toLocaleString() },
        { key: 'userName', label: 'Usuario' },
        { key: 'action', label: 'Accion' },
        { key: 'entity', label: 'Entidad' },
        { key: 'description', label: 'Descripcion' },
      ]} />
    </section>
  )
}
