import { useEffect, useState } from 'react'
import { inventoryApi } from '../../api/inventoryApi'
import { productApi } from '../../api/productApi'
import Button from '../../components/common/Button'
import Input from '../../components/common/Input'
import Select from '../../components/common/Select'

export default function MovementForm({ type = 'ENTRADA' }) {
  const [products, setProducts] = useState([])
  const [form, setForm] = useState({ productId: '', quantity: 1, reason: '' })
  const [message, setMessage] = useState('')
  useEffect(() => { productApi.list().then(setProducts) }, [])
  async function submit(event) {
    event.preventDefault()
    setMessage('')
    const payload = { productId: Number(form.productId), movementType: type, quantity: Number(form.quantity), reason: form.reason }
    if (type === 'SALIDA') await inventoryApi.exit(payload)
    else if (type === 'AJUSTE') await inventoryApi.adjustment(payload)
    else await inventoryApi.entry(payload)
    setForm({ productId: '', quantity: 1, reason: '' })
    setMessage('Movimiento registrado')
  }
  return (
    <form onSubmit={submit} className="grid gap-3 rounded-lg border border-slate-200 bg-white p-4 md:grid-cols-[2fr_1fr_2fr_auto]">
      <Select label="Producto" value={form.productId} onChange={(e) => setForm({ ...form, productId: e.target.value })} required>
        <option value="">Seleccionar</option>
        {products.map((p) => <option key={p.id} value={p.id}>{p.name} ({p.currentStock})</option>)}
      </Select>
      <Input label={type === 'AJUSTE' ? 'Nuevo stock' : 'Cantidad'} type="number" min="0" value={form.quantity} onChange={(e) => setForm({ ...form, quantity: e.target.value })} required />
      <Input label="Motivo" value={form.reason} onChange={(e) => setForm({ ...form, reason: e.target.value })} />
      <div className="self-end"><Button>{type}</Button></div>
      {message && <p className="text-sm font-semibold text-teal-700 md:col-span-4">{message}</p>}
    </form>
  )
}
