import { useMutation, useQueryClient } from '@tanstack/react-query'
import { type Room, type PostQuestionResponse } from './types'
import { api } from '../lib/api'

export function usePostQuestion<T=any>(url: string, key: string) {
  const qClient = useQueryClient()

  return useMutation({
    mutationFn: async (payload: T)=>{
      const data = await api.post<PostQuestionResponse>(url, payload)
      return data
    },
    onMutate({ question }: any) {
      const room = qClient.getQueryData<Room>([key])
      const questionsArray = room?.questions ?? []

      const newQuestion = {
        id: crypto.randomUUID(),
        roomId: '',
        question,
        answer: null,
        createdAt: new Date().toISOString(),
        isGeneratingAnswer: true,
      }

      qClient.setQueryData<Room>(
        [key],
        {...room!, questions:[newQuestion, ...questionsArray]}
      )

      return { newQuestion, room }
    },
    onSuccess({ data }, _variables, context) {
      qClient.setQueryData<Room>(
        [key],
        (room) => {
          if (!room) {
            return room
          }

          if (!context.newQuestion) {
            return room
          }

          return {...room!, questions: room.questions.map((question) => {
            if (question.id === context.newQuestion.id) {
              return {
                ...context.newQuestion,
                id: data.questionId ?? '',
                answer: data.answer.startsWith('Não possuo informações suficientes para responder') ? null :data.answer,
                isGeneratingAnswer: false,
              }
            }

            return question
          })}
        }
      )
    },
    onError(_error, _variables, context) {
      if (context?.room) {
        qClient.setQueryData<Room>(
          [key],
          context.room
        )
      }
    },
  })
}
