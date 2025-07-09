import { useParams } from 'react-router'

export function Room() {
  const { slug } = useParams()

  return (
    <p>Hello, {slug}!</p>
  )
}
