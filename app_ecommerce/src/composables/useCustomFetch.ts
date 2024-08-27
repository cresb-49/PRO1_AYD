import { useCookies } from 'vue3-cookies'
import { createFetch, useFetch, type UseFetchOptions } from '@vueuse/core'

export function useCustomFetch<T>(
  url: string,
  options: RequestInit = {}
) {

  return useCustomFetchPartial<T>(url, options).json()
}

const useCustomFetchPartial = createFetch({
  baseUrl: import.meta.env.VITE_API_BASE_URL,
  options: {
    async beforeFetch({ options }) {
      const userAuth = useCookies().cookies.get('user-token')
      options.headers = userAuth 
      ? {
        ...options.headers,
        Authorization: `Bearer ${userAuth}`,
        'Content-Type': 'application/json'
      } 
      : {
        ...options.headers,
        'Content-Type': 'application/json'
      }

      return { options }
    },
  },
})