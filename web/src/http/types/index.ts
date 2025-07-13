export interface PostQuestionResponse {
  questionId: string
  answer: string
}

export interface Question {
  id: string
  roomId: string
  question: string
  answer?: string | null
  createdAt: string
  isGeneratingAnswer?: boolean
}

export interface Room {
  slug: string
  title: string
  description: string
  createdAt: string
  questionsCount?: number | null
  questions: Question[]
}
