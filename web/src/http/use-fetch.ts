import { useQuery } from '@tanstack/react-query'
import { api } from '@/lib/api'

/*const opts = {
  method: 'GET',
  mode: 'cors',
  headers: {
    Accept: 'application/json'
  }
}*/

export function useFetch<T=any>(url:String, key:String) {
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
