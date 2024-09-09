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

async function openPDF(stream: ReadableStream) {
  // Crea el reader del stream
  const reader = stream.getReader();
  const chunks: Uint8Array[] = [];

  // Lee los chunks del stream i los mete al array
  let done = false;
  while (!done) {
    const { value, done: isDone } = await reader.read();
    if (value) {
      chunks.push(value);
    }
    done = isDone;
  }

  // Combina los chunks
  const pdfBytes = new Uint8Array(chunks.reduce((acc, chunk) => acc + chunk.length, 0));
  let offset = 0;
  chunks.forEach(chunk => {
    pdfBytes.set(chunk, offset);
    offset += chunk.length;
  });

  // Crea el blob del array
  const pdfBlob = new Blob([pdfBytes], { type: 'application/pdf' });

  // Crea el URL del blob
  const blobUrl = URL.createObjectURL(pdfBlob);

  // Abre el pdf
  window.open(blobUrl, '_blank'); 
}

const useCustomFetchPartial = createFetch({
  baseUrl: import.meta.env.VITE_API_BASE_URL,
  options: {
    updateDataOnError: true,
    onFetchError(ctx) {
      if (ctx.data === null) {
        if (ctx.response) {
          if (ctx.response.headers.has('content-type')) {
            if (ctx.response.headers.get('content-type') === 'application/pdf') {
              openPDF(ctx.response.body as ReadableStream)
              ctx.error = null
              return ctx;
            }
          }
          return ctx;
        }
        return ctx;
      }
      ctx.error = ctx.data.error as string
      return ctx
    },
    afterFetch(ctx) {
      console.log('response')
      console.log(ctx)
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
      } 
      : {
        ...options.headers,
      }

      return { options }
    },
  },
})