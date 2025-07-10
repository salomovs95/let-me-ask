import { useMutation, useQueryClient } from '@tanstack/react-query'
import { api } from '@/lib/api'

export function usePost<T=any>(url: String, key: String) {
  const qClient = useQueryClient()

  return useMutation({
    mutationFn: async (data: T)=>{
      await api.post(url, data)
    },
    onSuccess: ()=>{
      qClient.invalidateQueries({ queryKey:[key]})
    }
  })
}
