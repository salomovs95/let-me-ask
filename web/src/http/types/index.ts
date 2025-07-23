export interface PostQuestionResponse {
  questionId: string
  answer: string
  answerSimilarity: number
}

export interface Question {
  id: string
  roomId: string
  question: string
  answer: {
    answerText: string
    answerSimilarity: number
  }
  createdAt: string
  isGeneratingAnswer?: boolean
}

export interface Room {
  slug: string
  title: string
  description: string
  createdAt: string
  questionsCount: number
}
