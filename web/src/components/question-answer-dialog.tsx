import { Bot } from 'lucide-react'
import { Button } from "./ui/button"

import {
  AlertDialog,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
} from "./ui/alert-dialog"

type DialogProps = {
  title: string;
  text: string;
  open: boolean;
  onClose: any
}

export function QuestionAnswerDialog(props: DialogProps) {
  return (
    <AlertDialog open={props.open} onOpenChange={props.onClose}>
      <AlertDialogContent>
        <AlertDialogHeader>
          <AlertDialogTitle className="flex items-center gap-4 text-zinc-950 text-left text-2xl font-semibold">
            <Bot className="size-8 text-semibold text-secondary-foreground" />
            {props.title}
          </AlertDialogTitle>
          <AlertDialogDescription className="text-justify">
            {props.text}
          </AlertDialogDescription>
        </AlertDialogHeader>

        <AlertDialogFooter>
          <AlertDialogCancel asChild>
            <Button variant="secondary" className="bg-zinc-950 text-[#F0F2F5]">Close</Button>
          </AlertDialogCancel>
        </AlertDialogFooter>
      </AlertDialogContent>
    </AlertDialog>
  )
}
