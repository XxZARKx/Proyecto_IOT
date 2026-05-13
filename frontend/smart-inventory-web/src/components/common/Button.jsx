import clsx from 'clsx'

export default function Button({ children, className, variant = 'primary', ...props }) {
  const styles = {
    primary: 'bg-teal-700 text-white hover:bg-teal-800',
    secondary: 'bg-white text-slate-800 border border-slate-200 hover:bg-slate-50',
    danger: 'bg-rose-600 text-white hover:bg-rose-700',
  }
  return (
    <button className={clsx('inline-flex h-10 items-center justify-center gap-2 rounded-md px-4 text-sm font-semibold transition disabled:cursor-not-allowed disabled:opacity-60', styles[variant], className)} {...props}>
      {children}
    </button>
  )
}
