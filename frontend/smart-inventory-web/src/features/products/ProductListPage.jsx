import { useState } from 'react'
import { Link } from 'react-router-dom'
import { productApi } from '../../api/productApi'
import Button from '../../components/common/Button'
import Input from '../../components/common/Input'
import Table from '../../components/common/Table'
import Badge from '../../components/common/Badge'
import Spinner from '../../components/common/Spinner'
import { useFetch } from '../../hooks/useFetch'

export default function ProductListPage() {
  const [search, setSearch] = useState('')
  const { data, loading, reload } = useFetch(() => productApi.list(search), [search])
  return (
    <section className="grid gap-4">
      <div className="flex flex-wrap items-end justify-between gap-3">
        <div><h1 className="text-2xl font-bold text-slate-950">Productos</h1><p className="text-sm text-slate-500">Busqueda, stock y estados.</p></div>
        <Link to="/products/create"><Button>Nuevo producto</Button></Link>
      </div>
      <div className="flex gap-3"><Input label="Buscar" value={search} onChange={(e) => setSearch(e.target.value)} /><Button variant="secondary" onClick={reload}>Actualizar</Button></div>
      {loading ? <Spinner /> : <Table rows={data || []} columns={[
        { key: 'sku', label: 'SKU' },
        { key: 'name', label: 'Producto' },
        { key: 'categoryName', label: 'Categoria' },
        { key: 'currentStock', label: 'Stock' },
        { key: 'minimumStock', label: 'Min' },
        { key: 'status', label: 'Estado', render: (r) => <Badge>{r.status}</Badge> },
        { key: 'id', label: 'Detalle', render: (r) => <Link className="font-semibold text-teal-700" to={`/products/${r.id}`}>Ver</Link> },
      ]} />}
    </section>
  )
}
