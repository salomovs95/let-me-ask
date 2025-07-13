import { useMutation, useQueryClient } from '@tanstack/react-query'
import { api } from '../lib/api'

export function useCreateRoom<T=any>(url: string, key: string) {
  const qClient = useQueryClient()

  return useMutation({
    mutationFn: async (data: T)=>{
      return await api.post(url, data)
    },
    onSuccess: ()=>{
      qClient.invalidateQueries({ queryKey:[key]})
    }
  })
}
