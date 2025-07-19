import { zodResolver } from '@hookform/resolvers/zod'
import { useForm } from 'react-hook-form'
import { z } from 'zod'

import { useUpdateAnswer } from '../http/use-update-answer'
import { Button } from './ui/button'
import { Textarea } from './ui/textarea'

import {
  Card,
  CardContent,
  CardHeader,
  CardTitle,
} from './ui/card'

import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormMessage
} from './ui/form'

const createAnswerSchema = z.object({
  answer: z.string()
    .min(10, 'Answer is mamdatory')
    .min(10, 'Answer must have at least 10 characters')
    .max(500, 'Answer must not trespass 500 characters long')
})

type CreateAnswerFormData = z.infer<typeof createAnswerSchema>
type QuestionAnswerFormProps = {
  roomSlug: string;
  questionId: string;
}

type UpdateAnswerPayload = {
  questionId: string
  answer: string
}

export function QuestionAnswerForm({ questionId, roomSlug }: QuestionAnswerFormProps) {
  const { mutateAsync: updateAnswer } = useUpdateAnswer<UpdateAnswerPayload>(`/rooms/${roomSlug}/questions`, roomSlug)

  const form = useForm<CreateAnswerFormData>({
    resolver: zodResolver(createAnswerSchema),
    defaultValues: { answer: '' }
  })

  async function handleCreateAnswer({ answer }: CreateAnswerFormData) {
    await updateAnswer({ answer, questionId })
    form.reset()
  }

  const { isSubmitting } = form.formState

  return (
    <Card className="gap-4">
      <CardHeader>
        <CardTitle className="text-sm">
          Answer
        </CardTitle>
      </CardHeader>

      <CardContent>
        <Form {...form}>
          <form
            className="flex flex-col gap-4"
            onSubmit={form.handleSubmit(handleCreateAnswer)}
          >
            <FormField name="answer" render={({ field })=>(
              <FormItem>
                <FormControl>
                  <Textarea
                    className="min-h-[100px]"
                    disabled={isSubmitting}
                    placeholder="Place the answer here."
                    {...field}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )} />
            <Button disabled={isSubmitting} type="submit" variant="secondary" className="bg-zinc-900 text-zinc-50">Send answer</Button>
          </form>
        </Form> 
      </CardContent>
    </Card>
  )
}
