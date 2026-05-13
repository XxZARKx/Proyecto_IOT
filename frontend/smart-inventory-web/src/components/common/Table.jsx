export default function Table({ columns, rows, empty = 'Sin datos para mostrar' }) {
  return (
    <div className="overflow-hidden rounded-lg border border-slate-200 bg-white">
      <div className="overflow-x-auto">
        <table className="min-w-full text-left text-sm">
          <thead className="bg-slate-50 text-xs uppercase tracking-wide text-slate-500">
            <tr>{columns.map((c) => <th key={c.key} className="px-4 py-3">{c.label}</th>)}</tr>
          </thead>
          <tbody className="divide-y divide-slate-100">
            {rows?.length ? rows.map((row, index) => (
              <tr key={row.id || index} className="hover:bg-slate-50">
                {columns.map((c) => <td key={c.key} className="px-4 py-3 text-slate-700">{c.render ? c.render(row) : row[c.key]}</td>)}
              </tr>
            )) : <tr><td className="px-4 py-8 text-center text-slate-500" colSpan={columns.length}>{empty}</td></tr>}
          </tbody>
        </table>
      </div>
    </div>
  )
}
