export default function Input({ label, error, ...props }) {
  return (
    <label className="grid gap-1.5 text-sm font-medium text-slate-700">
      {label}
      <input className="h-10 rounded-md border border-slate-200 bg-white px-3 text-sm outline-none ring-teal-700/20 transition focus:border-teal-700 focus:ring-4" {...props} />
      {error && <span className="text-xs text-rose-600">{error}</span>}
    </label>
  )
}
