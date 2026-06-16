import axios from "axios"
import router from '@/router'

const apiClient = axios.create({
  baseURL: 'http://localhost:8080/api',
  withCredentials: true,           // sends cookies cross-origin
})

apiClient.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response && error.response.status === 404 ) {
      router.push('/404-not-found');
      return new Promise(() => {})
    }
  }
)

export default apiClient
