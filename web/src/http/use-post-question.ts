import { useMutation, useQueryClient } from '@tanstack/react-query'
import { api } from '@/lib/api'

interface Question {
  id: String
  roomId: String
  question: String
  answer?: String | null
  createdAt: String
  isGeneratingAnswer?: boolean
}

interface Room {
  questions: Question[]
}

export function usePostQuestion<T=any>(url: String, key: String) {
  const qClient = useQueryClient()

  return useMutation({
    mutationFn: async (data: T)=>{
      await api.post(url, data)
    },
    onMutate({ question }) {
      const room = qClient.getQueryData<Room>([key])
      const questionsArray = room.questions ?? []

      const newQuestion = {
        id: crypto.randomUUID(),
        question,
        answer: null,
        createdAt: new Date().toISOString(),
        isGeneratingAnswer: true,
      }

      qClient.setQueryData<Room>(
        [key],
        {...room, questions:[newQuestion, ...questionsArray]}
      )

      return { newQuestion, room }
    },
    onSuccess(data, _variables, context) {
      qClient.setQueryData<Room>(
        [key],
        (room) => {
          if (!room) {
            return room
          }

          if (!context.newQuestion) {
            return room
          }

          return {...room, questions: questions.map((question) => {
            if (question.id === context.newQuestion.id) {
              return {
                ...context.newQuestion,
                id: data.questionId,
                answer: data.answer,
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
