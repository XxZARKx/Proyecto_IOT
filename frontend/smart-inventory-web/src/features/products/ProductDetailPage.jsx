import { Link, useParams } from 'react-router-dom'
import Button from '../../components/common/Button'
import { inventoryApi } from '../../api/inventoryApi'
import { productApi } from '../../api/productApi'
import Table from '../../components/common/Table'
import Spinner from '../../components/common/Spinner'
import { useFetch } from '../../hooks/useFetch'

export default function ProductDetailPage() {
  const { id } = useParams()
  const products = useFetch(() => productApi.list(), [])
  const history = useFetch(() => inventoryApi.history(id), [id])
  const product = products.data?.find((p) => String(p.id) === id)
  if (products.loading || history.loading) return <Spinner />
  return (
    <section className="grid gap-4">
      <div className="rounded-lg border border-slate-200 bg-white p-4">
        <div className="flex flex-wrap items-start justify-between gap-3">
          <div>
            <h1 className="text-2xl font-bold text-slate-950">{product?.name}</h1>
            <p className="text-sm text-slate-500">{product?.sku} · {product?.categoryName}</p>
            <p className="mt-3 text-sm text-slate-700">Stock actual: <b>{product?.currentStock}</b> | Min: {product?.minimumStock} | Max: {product?.maximumStock}</p>
          </div>
          <Link to={`/products/${id}/edit`}><Button variant="secondary">Editar</Button></Link>
        </div>
      </div>
      <Table rows={history.data || []} columns={[{ key: 'createdAt', label: 'Fecha', render: (r) => new Date(r.createdAt).toLocaleString() }, { key: 'movementType', label: 'Tipo' }, { key: 'quantity', label: 'Cantidad' }, { key: 'previousStock', label: 'Antes' }, { key: 'newStock', label: 'Despues' }, { key: 'source', label: 'Origen' }]} />
    </section>
  )
}
