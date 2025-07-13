import { ArrowLeft, Radio } from 'lucide-react'
import { Link, Navigate, useParams } from 'react-router-dom'
import { QuestionForm } from '../components/question-form'
import { QuestionList } from '../components/question-list'
import { Button } from '../components/ui/button'

type RoomParams = {
  roomSlug: string
}

export function Room() {
  const params = useParams<RoomParams>()

  if (!params.roomSlug) {
    return <Navigate replace to="/" />
  }

  return (
    <div className="min-h-screen bg-zinc-950">
      <div className="container mx-auto max-w-4xl px-4 py-8">
        <div className="mb-8">
          <div className="mb-4 flex items-center justify-between">
            <Link to="/">
              <Button variant="secondary">
                <ArrowLeft className="mr-2 size-4" />
                To the start
              </Button>
            </Link>
            <Link to={`/rooms/${params.roomSlug}/audio`}>
              <Button className="flex items-center gap-2" variant="secondary">
                <Radio className="size-4" />
                Record Audio
              </Button>
            </Link>
          </div>
          <h1 className="mb-2 font-bold text-3xl text-foreground">
            Questions Room
          </h1>
          <p className="text-muted-foreground">
            Ask questions and get answers from AI
          </p>
        </div>

        <div className="mb-8">
          <QuestionForm roomSlug={params.roomSlug} />
        </div>

        <QuestionList roomSlug={params.roomSlug} />
      </div>
    </div>
  )
}
