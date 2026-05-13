import { Bar, BarChart, ResponsiveContainer, Tooltip, XAxis, YAxis } from 'recharts'

export default function TopProductsChart({ data = [] }) {
  return (
    <div className="h-72 rounded-lg border border-slate-200 bg-white p-4">
      <p className="mb-3 text-sm font-semibold text-slate-800">Productos mas vendidos</p>
      <ResponsiveContainer width="100%" height="88%">
        <BarChart data={data} layout="vertical" margin={{ left: 24 }}>
          <XAxis type="number" />
          <YAxis type="category" dataKey="productName" width={120} />
          <Tooltip />
          <Bar dataKey="totalOut" fill="#2563eb" radius={[0, 4, 4, 0]} />
        </BarChart>
      </ResponsiveContainer>
    </div>
  )
}
