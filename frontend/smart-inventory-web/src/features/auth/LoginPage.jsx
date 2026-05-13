import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { ShieldCheck } from 'lucide-react'
import Button from '../../components/common/Button'
import Input from '../../components/common/Input'
import { useAuth } from '../../app/providers/AuthProvider'

export default function LoginPage() {
  const { login, loading } = useAuth()
  const navigate = useNavigate()
  const [form, setForm] = useState({ email: 'admin@smartinventory.local', password: 'Password123' })
  const [error, setError] = useState('')

  async function submit(event) {
    event.preventDefault()
    setError('')
    try {
      await login(form)
      navigate('/dashboard')
    } catch (err) {
      setError(err.response?.data?.error || 'No se pudo iniciar sesion')
    }
  }

  return (
    <main className="grid min-h-screen place-items-center bg-slate-100 px-4">
      <form onSubmit={submit} className="w-full max-w-md rounded-lg border border-slate-200 bg-white p-6 shadow-sm">
        <div className="mb-6 flex items-center gap-3">
          <div className="grid h-11 w-11 place-items-center rounded-md bg-teal-700 text-white"><ShieldCheck /></div>
          <div>
            <h1 className="text-2xl font-bold text-slate-950">StockIQ IoT</h1>
            <p className="text-sm text-slate-500">Control inteligente de inventario</p>
          </div>
        </div>
        <div className="grid gap-4">
          <Input label="Email" value={form.email} onChange={(e) => setForm({ ...form, email: e.target.value })} />
          <Input label="Password" type="password" value={form.password} onChange={(e) => setForm({ ...form, password: e.target.value })} />
          {error && <p className="rounded-md bg-rose-50 px-3 py-2 text-sm text-rose-700">{error}</p>}
          <Button disabled={loading}>{loading ? 'Ingresando...' : 'Ingresar'}</Button>
        </div>
      </form>
    </main>
  )
}
