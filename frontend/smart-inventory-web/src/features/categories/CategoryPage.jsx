import { useState } from 'react'
import { categoryApi } from '../../api/categoryApi'
import Button from '../../components/common/Button'
import Input from '../../components/common/Input'
import Table from '../../components/common/Table'
import { useFetch } from '../../hooks/useFetch'

export default function CategoryPage() {
  const { data, reload } = useFetch(categoryApi.list, [])
  const [form, setForm] = useState({ name: '', description: '' })
  async function submit(event) {
    event.preventDefault()
    await categoryApi.create(form)
    setForm({ name: '', description: '' })
    reload()
  }
  return (
    <section className="grid gap-4">
      <h1 className="text-2xl font-bold text-slate-950">Categorias</h1>
      <form onSubmit={submit} className="grid gap-3 rounded-lg border border-slate-200 bg-white p-4 md:grid-cols-[1fr_2fr_auto]">
        <Input label="Nombre" value={form.name} onChange={(e) => setForm({ ...form, name: e.target.value })} required />
        <Input label="Descripcion" value={form.description} onChange={(e) => setForm({ ...form, description: e.target.value })} />
        <div className="self-end"><Button>Crear</Button></div>
      </form>
      <Table rows={data || []} columns={[{ key: 'name', label: 'Nombre' }, { key: 'description', label: 'Descripcion' }, { key: 'active', label: 'Estado', render: (r) => r.active ? 'Activo' : 'Inactivo' }]} />
    </section>
  )
}
