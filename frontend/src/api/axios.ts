import axios from "axios"

export const api = axios.create({
  baseURL: 'http://localhost:8080',
  withCredentials: true,           // sends cookies cross-origin

  // CSRF Cookies - (default names - change if server changes from defaults)
  xsrfCookieName: 'XSRF-TOKEN',
  xsrfHeaderName: 'X-XSRF-TOKEN',
})
