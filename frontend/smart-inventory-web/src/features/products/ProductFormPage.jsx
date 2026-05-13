import { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import { categoryApi } from '../../api/categoryApi'
import { productApi } from '../../api/productApi'
import Button from '../../components/common/Button'
import Input from '../../components/common/Input'
import Select from '../../components/common/Select'

const empty = { name: '', description: '', sku: '', barcode: '', unitPrice: 0, currentStock: 0, minimumStock: 1, maximumStock: 100, categoryId: '' }

export default function ProductFormPage() {
  const { id } = useParams()
  const navigate = useNavigate()
  const [form, setForm] = useState(empty)
  const [categories, setCategories] = useState([])
  const [error, setError] = useState('')

  useEffect(() => {
    categoryApi.list().then(setCategories)
    if (id) productApi.list().then((items) => {
      const product = items.find((p) => String(p.id) === id)
      if (product) setForm({ ...product, categoryId: product.categoryId })
    })
  }, [id])

  async function submit(event) {
    event.preventDefault()
    setError('')
    const payload = { ...form, unitPrice: Number(form.unitPrice), currentStock: Number(form.currentStock), minimumStock: Number(form.minimumStock), maximumStock: Number(form.maximumStock), categoryId: Number(form.categoryId) }
    try {
      id ? await productApi.update(id, payload) : await productApi.create(payload)
      navigate('/products')
    } catch (err) {
      setError(err.response?.data?.error || err.message)
    }
  }

  return (
    <form onSubmit={submit} className="grid max-w-3xl gap-4">
      <div><h1 className="text-2xl font-bold text-slate-950">{id ? 'Editar producto' : 'Crear producto'}</h1></div>
      <div className="grid gap-4 rounded-lg border border-slate-200 bg-white p-4 md:grid-cols-2">
        <Input label="Nombre" value={form.name} onChange={(e) => setForm({ ...form, name: e.target.value })} required />
        <Input label="SKU" value={form.sku} onChange={(e) => setForm({ ...form, sku: e.target.value })} required disabled={Boolean(id)} />
        <Input label="Codigo de barras" value={form.barcode || ''} onChange={(e) => setForm({ ...form, barcode: e.target.value })} />
        <Input label="Precio" type="number" step="0.01" value={form.unitPrice} onChange={(e) => setForm({ ...form, unitPrice: e.target.value })} required />
        <Input label="Stock actual" type="number" value={form.currentStock} onChange={(e) => setForm({ ...form, currentStock: e.target.value })} required disabled={Boolean(id)} />
        <Input label="Stock minimo" type="number" value={form.minimumStock} onChange={(e) => setForm({ ...form, minimumStock: e.target.value })} required />
        <Input label="Stock maximo" type="number" value={form.maximumStock} onChange={(e) => setForm({ ...form, maximumStock: e.target.value })} required />
        <Select label="Categoria" value={form.categoryId} onChange={(e) => setForm({ ...form, categoryId: e.target.value })} required>
          <option value="">Seleccionar</option>
          {categories.map((c) => <option key={c.id} value={c.id}>{c.name}</option>)}
        </Select>
        <label className="grid gap-1.5 text-sm font-medium text-slate-700 md:col-span-2">Descripcion
          <textarea className="min-h-24 rounded-md border border-slate-200 p-3" value={form.description || ''} onChange={(e) => setForm({ ...form, description: e.target.value })} />
        </label>
        {error && <p className="rounded-md bg-rose-50 p-3 text-sm text-rose-700 md:col-span-2">{error}</p>}
      </div>
      <Button>Guardar</Button>
    </form>
  )
}
