import { useCustomFetch } from '@/composables/useCustomFetch'
import { defineStore } from 'pinia'

export enum SnackbarType {
  SUCCESS,
  ERROR,
  WARNING,
  MESSAGE
}

export type CreationPayload = {
  name: string,
  categoriaPadre: number
}
 
export type Category = {
  id: number,
  nombre: string,
  id_padre: number
}

export const useSnackbarStore = defineStore('categories', {
  state: () => ({
    categories: Array<Category>,
  }),
  actions: {
    async createCategory(payload: CreationPayload) {
      const { name,categoriaPadre } = payload;
      
      const { data, error } = await useCustomFetch<any>(
        'api/usuario/private/crearCategoria',
        {
          method: 'POST',
          body: JSON.stringify(payload)
        }
      )
    },
    hideSnackbar() {
      this.snackbarShow = false
    }
  }
})
