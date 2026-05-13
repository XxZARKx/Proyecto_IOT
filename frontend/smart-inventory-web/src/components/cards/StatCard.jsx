export default function StatCard({ title, value, icon: Icon }) {
  return (
    <div className="rounded-lg border border-slate-200 bg-white p-4">
      <div className="flex items-center justify-between">
        <p className="text-sm font-medium text-slate-500">{title}</p>
        {Icon && <Icon className="h-5 w-5 text-teal-700" />}
      </div>
      <p className="mt-3 text-3xl font-bold text-slate-900">{value ?? 0}</p>
    </div>
  )
}
