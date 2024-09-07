import { useCookies } from 'vue3-cookies'
import { createFetch, useFetch, type UseFetchOptions } from '@vueuse/core'

export function useCustomFetch<T>(
  url: string,
  options: RequestInit = {},
  multipart = false,
) {

  if (multipart) {
    return useCustomFetchPartialMultipart<T>(url, options).json()
  }
  return useCustomFetchPartial<T>(url, options).json()
}

const useCustomFetchPartial = createFetch({
  baseUrl: import.meta.env.VITE_API_BASE_URL,
  options: {
    updateDataOnError: true,
    onFetchError(ctx) {
      ctx.error = ctx.data.error as string
      return ctx
    },
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

const useCustomFetchPartialMultipart = createFetch({
  baseUrl: import.meta.env.VITE_API_BASE_URL,
  options: {
    async beforeFetch({ options }) {
      const userAuth = useCookies().cookies.get('user-token')
      options.headers = userAuth 
      ? {
        ...options.headers,
        Authorization: `Bearer ${userAuth}`,
      } 
      : {
        ...options.headers,
      }

      return { options }
    },
  },
})