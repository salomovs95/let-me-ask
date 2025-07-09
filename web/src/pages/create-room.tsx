import { useEffect } from 'react'
import { Link } from 'react-router-dom'

import { useFetch } from '@/http/use-fetch'

type Room = {
  slug: String,
  title: String
}

type GetRoomResponse = Array<Room>

export function CreateRoom() {
  //const API_URL = `${import.meta.env.VITE_API_URL}/rooms/`
  const { isLoading, data, error } = useFetch<GetRoomResponse>('/rooms/', 'rooms_list')

  return (
    <>
      {error && <p className="text-red-600">{error.message}</p>}
      {isLoading && <p>Loading...</p>}
      {data?.map((room:Room)=>(
        <Link key={room.slug} to={`/rooms`}>
          <span>{room.title}</span>
          <span>entrar →</span>
        </Link>
      ))}
    </>
  )
}
