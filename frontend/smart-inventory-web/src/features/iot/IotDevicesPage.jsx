import { useState } from 'react'
import { iotApi } from '../../api/iotApi'
import Button from '../../components/common/Button'
import Input from '../../components/common/Input'
import Select from '../../components/common/Select'
import Table from '../../components/common/Table'
import { useFetch } from '../../hooks/useFetch'

export default function IotDevicesPage() {
  const { data, reload } = useFetch(iotApi.devices, [])
  const [form, setForm] = useState({ code: '', name: '', sensorType: 'RFID', location: 'Almacen principal', status: 'ACTIVO' })
  async function submit(event) {
    event.preventDefault()
    await iotApi.createDevice(form)
    setForm({ code: '', name: '', sensorType: 'RFID', location: '', status: 'ACTIVO' })
    reload()
  }
  return (
    <section className="grid gap-4">
      <h1 className="text-2xl font-bold text-slate-950">Dispositivos IoT</h1>
      <form onSubmit={submit} className="grid gap-3 rounded-lg border border-slate-200 bg-white p-4 md:grid-cols-5">
        <Input label="Codigo" value={form.code} onChange={(e) => setForm({ ...form, code: e.target.value })} required />
        <Input label="Nombre" value={form.name} onChange={(e) => setForm({ ...form, name: e.target.value })} required />
        <Select label="Sensor" value={form.sensorType} onChange={(e) => setForm({ ...form, sensorType: e.target.value })}><option>RFID</option><option>QR</option><option>BARCODE</option><option>WEIGHT_SENSOR</option><option>SHELF_SENSOR</option></Select>
        <Input label="Ubicacion" value={form.location} onChange={(e) => setForm({ ...form, location: e.target.value })} />
        <div className="self-end"><Button>Crear</Button></div>
      </form>
      <Table rows={data || []} columns={[{ key: 'code', label: 'Codigo' }, { key: 'name', label: 'Nombre' }, { key: 'sensorType', label: 'Tipo' }, { key: 'location', label: 'Ubicacion' }, { key: 'status', label: 'Estado' }]} />
    </section>
  )
}
