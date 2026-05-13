import { useCallback, useEffect, useState } from 'react'

export function useFetch(loader, deps = []) {
  const [data, setData] = useState(null)
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')

  const load = useCallback(async () => {
    setLoading(true)
    setError('')
    try {
      setData(await loader())
    } catch (err) {
      setError(err.response?.data?.error || err.message)
    } finally {
      setLoading(false)
    }
  }, deps)

  useEffect(() => { load() }, [load])
  return { data, loading, error, reload: load }
}
