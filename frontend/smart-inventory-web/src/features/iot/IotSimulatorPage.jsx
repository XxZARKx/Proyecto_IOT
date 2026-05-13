import { useEffect, useState } from 'react'
import { iotApi } from '../../api/iotApi'
import { productApi } from '../../api/productApi'
import Button from '../../components/common/Button'
import Input from '../../components/common/Input'
import Select from '../../components/common/Select'

export default function IotSimulatorPage() {
  const [devices, setDevices] = useState([])
  const [products, setProducts] = useState([])
  const [form, setForm] = useState({ deviceCode: '', productSku: '', sensorType: 'RFID', quantityDetected: 1, movementType: 'ENTRADA', location: 'Almacen principal', processAsMovement: true })
  const [message, setMessage] = useState('')
  useEffect(() => { iotApi.devices().then(setDevices); productApi.list().then(setProducts) }, [])
  async function submit(event) {
    event.preventDefault()
    await iotApi.createReading({ ...form, quantityDetected: Number(form.quantityDetected) })
    setMessage('Lectura enviada y procesada')
  }
  async function auto() {
    await iotApi.simulate()
    setMessage('Lectura automatica generada')
  }
  return (
    <form onSubmit={submit} className="grid max-w-4xl gap-4">
      <h1 className="text-2xl font-bold text-slate-950">Simulador IoT</h1>
      <div className="grid gap-3 rounded-lg border border-slate-200 bg-white p-4 md:grid-cols-2">
        <Select label="Dispositivo" value={form.deviceCode} onChange={(e) => setForm({ ...form, deviceCode: e.target.value, sensorType: devices.find((d) => d.code === e.target.value)?.sensorType || 'RFID' })} required><option value="">Seleccionar</option>{devices.map((d) => <option key={d.id} value={d.code}>{d.code}</option>)}</Select>
        <Select label="Producto" value={form.productSku} onChange={(e) => setForm({ ...form, productSku: e.target.value })} required><option value="">Seleccionar</option>{products.map((p) => <option key={p.id} value={p.sku}>{p.name}</option>)}</Select>
        <Select label="Movimiento" value={form.movementType} onChange={(e) => setForm({ ...form, movementType: e.target.value })}><option>ENTRADA</option><option>SALIDA</option><option>AJUSTE</option><option>DEVOLUCION</option><option>MERMA</option></Select>
        <Input label="Cantidad detectada" type="number" min="1" value={form.quantityDetected} onChange={(e) => setForm({ ...form, quantityDetected: e.target.value })} />
        <Input label="Ubicacion" value={form.location} onChange={(e) => setForm({ ...form, location: e.target.value })} />
        <div className="flex items-end gap-2"><Button>Enviar lectura</Button><Button type="button" variant="secondary" onClick={auto}>Automatico</Button></div>
        {message && <p className="text-sm font-semibold text-teal-700 md:col-span-2">{message}</p>}
      </div>
    </form>
  )
}
