import { QueryClient, QueryClientProvider } from '@tanstack/react-query'
import { BrowserRouter, Routes, Route } from 'react-router-dom'

import { CreateRoom } from './pages/create-room'
import { RecordRoomAudio } from './pages/record-room-audio'
import { Room } from './pages/room'

const client = new QueryClient()
export function App() {
  return (
    <QueryClientProvider client={client}>
      <BrowserRouter>
        <Routes>
          <Route element={<CreateRoom />} index />
          <Route element={<Room />} path="/rooms/:roomSlug" />
          <Route element={<RecordRoomAudio />} path="/rooms/:roomSlug/audio" />
        </Routes>
      </BrowserRouter>
    </ QueryClientProvider>
  )
}
