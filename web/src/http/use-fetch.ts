import { useQuery } from '@tanstack/react-query'
import { api } from '@/lib/api'

/*const opts = {
  method: 'GET',
  mode: 'cors',
  headers: {
    Accept: 'application/json'
  }
}*/

export function useFetch<T>(url:String, key:String) {
  return useQuery({
    queryKey: [key],
    queryFn: async () => {
      //const response = await fetch(url, opts)
      //const data: T = await response.json()
      const { data } = await api.get(url)
      return data
    },
    //staleTime: 1000*4
  })
}
