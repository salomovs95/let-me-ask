import './index.css'

import { QueryClient, QueryClientProvider } from '@tanstack/react-query'
import { BrowserRouter, Routes, Route } from 'react-router-dom'

import { CreateRoom } from '@/pages/create-room'

const client = new QueryClient()
export function App() {
  return (
    <QueryClientProvider client={client}>
      <BrowserRouter>
        <Routes>
          <Route element={<CreateRoom />} index />
        </Routes>
      </BrowserRouter>
    </ QueryClientProvider>
  )
}
