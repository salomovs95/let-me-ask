import { useFetch } from '@/http/use-fetch'
import { QuestionItem } from './question-item'

interface QuestionListProps {
  roomSlug: string
}

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

export function QuestionList(props: QuestionListProps) {
  const { data } = useFetch<Room>(`/rooms/${props.roomSlug}`, props.roomSlug)

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <h2 className="font-semibold text-2xl text-foreground">
          Q&A
        </h2>
      </div>

      {data?.questions.map((question) => {
        return <QuestionItem key={question.id} question={question} />
      })}
    </div>
  )
}
