import { Cell, Pie, PieChart, ResponsiveContainer, Tooltip } from 'recharts'

export default function StockChart({ total = 0, low = 0, out = 0 }) {
  const data = [
    { name: 'Normal', value: Math.max(total - low, 0), color: '#0f766e' },
    { name: 'Bajo', value: low, color: '#b45309' },
    { name: 'Sin stock', value: out, color: '#e11d48' },
  ]
  return (
    <div className="h-72 rounded-lg border border-slate-200 bg-white p-4">
      <p className="mb-3 text-sm font-semibold text-slate-800">Estado de stock</p>
      <ResponsiveContainer width="100%" height="88%">
        <PieChart>
          <Pie data={data} dataKey="value" nameKey="name" innerRadius={48} outerRadius={92}>
            {data.map((entry) => <Cell key={entry.name} fill={entry.color} />)}
          </Pie>
          <Tooltip />
        </PieChart>
      </ResponsiveContainer>
    </div>
  )
}
