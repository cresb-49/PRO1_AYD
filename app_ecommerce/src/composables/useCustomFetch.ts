import { defu } from 'defu'
import { useCookies } from 'vue3-cookies'
import { useFetch, type UseFetchOptions } from '@vueuse/core'

export function useCustomFetch<T>(
  url: string,
  options: any = {}
) {
  const userAuth = useCookies().cookies.get('user-token')

  const defaults: any = {
    baseURL: import.meta.env.VITE_API_BASE_URL,
    // cache request
    key: url,

    // set user token if connected
    headers: userAuth
      ? {
          Authorization: `Bearer ${userAuth}`,
          'Content-Type': 'application/json'
        }
      : { 'Content-Type': 'application/json' },

    onResponse(_ctx) {
      // _ctx.response._data = new myBusinessResponse(_ctx.response._data)
    },

    onResponseError(_ctx) {
      // throw new myBusinessError()
    }
  }

  // for nice deep defaults, please use unjs/defu
  const params = defu(options, defaults)

  return useFetch<T>(url, params)
}
