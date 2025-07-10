import { ArrowRight } from 'lucide-react'
import { Link } from 'react-router-dom'
import { useFetch } from '@/http/use-fetch'
import { dayjs } from '@/lib/dayjs'
import { Badge } from './ui/badge'
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from './ui/card'

export function RoomList() {
  const { data, isLoading } = useFetch('/rooms', 'room-list')

  return (
    <Card>
      <CardHeader>
        <CardTitle>Latest rooms</CardTitle>
        <CardDescription>
          Quick access to rooms opened recently
        </CardDescription>
      </CardHeader>
      <CardContent className="flex flex-col gap-3">
        {isLoading && (
          <p className="text-muted-foreground text-sm">Fetching latest rooms...</p>
        )}

        {data?.map((room) => {
          return (
            <Link
              className="flex items-center justify-between rounded-lg border p-3 hover:bg-accent/50"
              key={room.slug}
              to={`/room/${room.slug}`}
            >
              <div className="flex flex-1 flex-col gap-1">
                <h3 className="font-medium">{room.title}</h3>

                <div className="flex items-center gap-2">
                  <Badge className="text-xs" variant="secondary">
                    {dayjs(room.createdAt).toNow()}
                  </Badge>
                  <Badge className="text-xs" variant="secondary">
                    {room.questionsCount ?? 0} questions(s)
                  </Badge>
                </div>
              </div>

              <span className="flex items-center gap-1 text-sm">
                Access
                <ArrowRight className="size-3" />
              </span>
            </Link>
          )
        })}
      </CardContent>
    </Card>
  )
}
