import { Bot, Loader2, MessageSquare } from 'lucide-react'
import { type Question } from '../http/types'
import { QuestionAnswerForm } from './question-answer-form'
import { Card, CardContent } from './ui/card'
import { dayjs } from '../lib/dayjs'

interface QuestionItemProps {
  roomSlug: string
  question: Question
}

export function QuestionItem({ question, roomSlug }: QuestionItemProps) {
  return (
    <Card>
      <CardContent>
        <div className="space-y-4">
          <div className="flex items-start space-x-3">
            <div className="flex-shrink-0">
              <div className="flex size-8 items-center justify-center rounded-full bg-primary/10">
                <MessageSquare className="size-4 text-primary" />
              </div>
            </div>
            <div className="flex-1">
              <p className="mb-1 font-medium text-foreground">Question</p>
              <p className="whitespace-pre-line text-muted-foreground text-sm leading-relaxed">
                {question.question}
              </p>
            </div>
          </div>

          {((!!question.answer && !question.answer.startsWith('Não possuo informações suficientes')) || question.isGeneratingAnswer) ? (
            <div className="flex items-start space-x-3">
              <div className="flex-shrink-0">
                <div className="flex size-8 items-center justify-center rounded-full bg-primary/10">
                  <Bot className="size-4 text-secondary-foreground" />
                </div>
              </div>
              <div className="flex-1">
                <p className="mb-1 font-medium text-foreground">
                  Answer from IA
                </p>
                <div className="text-muted-foreground">
                  {question.isGeneratingAnswer ? (
                    <div className="flex items-center space-x-2">
                      <Loader2 className="size-4 animate-spin text-primary" />
                      <span className="text-primary text-sm italic">
                        Generating answer...
                      </span>
                    </div>
                  ) : (
                    <p className="whitespace-pre-line text-sm leading-relaxed">
                      {question.answer}
                    </p>
                  )}
                </div>
              </div>
            </div>
          ) : (
            <QuestionAnswerForm roomSlug={roomSlug} questionId={question.id} />
          )}

          <div className="flex justify-end">
            <span className="text-muted-foreground text-xs">
              {dayjs(question.createdAt).toNow()}
            </span>
          </div>
        </div>
      </CardContent>
    </Card>
  )
}
