import { Bar, BarChart, CartesianGrid, ResponsiveContainer, Tooltip, XAxis, YAxis } from 'recharts'

export default function MovementChart({ data = [] }) {
  return (
    <div className="h-72 rounded-lg border border-slate-200 bg-white p-4">
      <p className="mb-3 text-sm font-semibold text-slate-800">Entradas vs salidas</p>
      <ResponsiveContainer width="100%" height="88%">
        <BarChart data={data}>
          <CartesianGrid strokeDasharray="3 3" vertical={false} />
          <XAxis dataKey="label" />
          <YAxis />
          <Tooltip />
          <Bar dataKey="entradas" fill="#0f766e" radius={[4, 4, 0, 0]} />
          <Bar dataKey="salidas" fill="#b45309" radius={[4, 4, 0, 0]} />
        </BarChart>
      </ResponsiveContainer>
    </div>
  )
}
