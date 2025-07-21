import { type Question } from '../http/types'

import { useFetch } from '../http/use-fetch'
import { QuestionItem } from './question-item'

interface QuestionListProps {
  roomSlug: string
}

export function QuestionList(props: QuestionListProps) {
  const { data } = useFetch<Question[]>(`/rooms/${props.roomSlug}/questions`, props.roomSlug)

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <h2 className="font-semibold text-2xl text-foreground">
          Q&A
        </h2>
      </div>

      {data?.map((question) => (
        <QuestionItem key={question.id} question={question} roomSlug={props.roomSlug} />
      ))}
    </div>
  )
}
