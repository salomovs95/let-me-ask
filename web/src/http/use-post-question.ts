import { useMutation, useQueryClient } from '@tanstack/react-query'
import { type Question, type PostQuestionResponse } from './types'
import { api } from '../lib/api'

export function usePostQuestion<T=any>(url: string, key: string) {
  const qClient = useQueryClient()

  return useMutation({
    mutationFn: async (payload: T)=>{
      const data = await api.post<PostQuestionResponse>(url, payload)
      return data
    },
    onMutate({ question }: any) {
      const questions = qClient.getQueryData<Question[]>([key]) ?? []

      const newQuestion = {
        id: crypto.randomUUID(),
        roomId: '',
        question,
        answer: {
          answerText: '',
          answerSimilarity: 0.0
        },
        createdAt: new Date().toISOString(),
        isGeneratingAnswer: true,
      }

      qClient.setQueryData<Question[]>(
        [key],
        [newQuestion, ...questions]
      )

      return { newQuestion, questions }
    },
    onSuccess({ data }, _variables, context) {
      qClient.setQueryData<Question[]>(
        [key],
        (questions:Question[]|undefined) => {
          if (!questions) {
            return []
          }

          if (!context.newQuestion) {
            return questions
          }

          return questions.map((question:Question) => {
            if (question.id === context.newQuestion.id) {
              return {
                ...context.newQuestion,
                id: data.questionId ?? '',
                answer: {
                  answerText: data.answer,
                  answerSimilarity: data.answerSimilarity
                },
                isGeneratingAnswer: false,
              }
            }

            return question
          })
        }
      )
    },
    onError(_error, _variables, context) {
      if (context?.questions) {
        qClient.setQueryData<Question[]>(
          [key],
          [...context.questions]
        )
      }
    },
  })
}
