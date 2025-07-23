import { useMutation, useQueryClient } from '@tanstack/react-query'
import { type Question } from './types'
import { api } from '../lib/api'

export function useUpdateAnswer<T=any>(url: string, key: string) {
  const qClient = useQueryClient()

  return useMutation({
    mutationFn: async (payload: T)=>{
      await api.patch(url, payload)
      return payload
    },
    onMutate: (payload)=>{
      //const room = qClient.getQueryData<Room>([key])
      return payload
    },
    onSuccess: (_data, _variables, context: any)=>{
      qClient.setQueryData(
        [key],
        (questions: Question[])=>{
          
          return questions.map((question: Question)=>{
            if (question.id == context.questionId) {
              return {
                ...question,
                answer: context.answer
              }
            }
            return question
          })
        }
      )
      // qClient.invalidateQueries({ queryKey:[key]})
    }
  })
}
