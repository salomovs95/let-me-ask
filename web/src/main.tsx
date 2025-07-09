import './index.css'
import eruda from 'eruda'

import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { App } from './app'

eruda.init()

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <App />
  </StrictMode>,
)
