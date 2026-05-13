import { useState } from 'react'
import { userApi } from '../../api/userApi'
import Button from '../../components/common/Button'
import Input from '../../components/common/Input'
import Select from '../../components/common/Select'
import Table from '../../components/common/Table'
import { useFetch } from '../../hooks/useFetch'

export default function UsersPage() {
  const { data, reload, error } = useFetch(userApi.list, [])
  const [form, setForm] = useState({ fullName: '', email: '', password: 'Password123', role: 'ENCARGADO_ALMACEN' })
  async function submit(event) {
    event.preventDefault()
    await userApi.create(form)
    setForm({ fullName: '', email: '', password: 'Password123', role: 'ENCARGADO_ALMACEN' })
    reload()
  }
  return (
    <section className="grid gap-4">
      <h1 className="text-2xl font-bold text-slate-950">Usuarios</h1>
      {error && <p className="rounded-md bg-amber-50 p-3 text-sm text-amber-800">Solo ADMIN puede gestionar usuarios.</p>}
      <form onSubmit={submit} className="grid gap-3 rounded-lg border border-slate-200 bg-white p-4 md:grid-cols-5">
        <Input label="Nombre" value={form.fullName} onChange={(e) => setForm({ ...form, fullName: e.target.value })} required />
        <Input label="Email" value={form.email} onChange={(e) => setForm({ ...form, email: e.target.value })} required />
        <Input label="Password" value={form.password} onChange={(e) => setForm({ ...form, password: e.target.value })} required />
        <Select label="Rol" value={form.role} onChange={(e) => setForm({ ...form, role: e.target.value })}><option>ADMIN</option><option>ENCARGADO_ALMACEN</option><option>GERENTE</option></Select>
        <div className="self-end"><Button>Crear</Button></div>
      </form>
      <Table rows={data || []} columns={[{ key: 'fullName', label: 'Nombre' }, { key: 'email', label: 'Email' }, { key: 'role', label: 'Rol' }, { key: 'status', label: 'Estado' }]} />
    </section>
  )
}
