import { useQuery } from '@tanstack/react-query'
import { api } from '../lib/api'

export function useFetch<T=any>(url:string, key:string) {
  return useQuery({
    queryKey: [key],
    queryFn: async () => {
      const result = await api.get(url)
      const data: T = result.data
      return data
    },
    //staleTime: 1000*4
  })
}
