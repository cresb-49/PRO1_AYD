import { useCustomFetch } from '@/composables/useCustomFetch'
import { defineStore } from 'pinia'
import { useSnackbarStore } from './snackbar'
import { convertError } from '@/utils/error-converter'

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

export const useCategoryStore = defineStore('categories', {
  state: () => ({
    categories: Array<Category>,
    loading: false,
    error: false
  }),
  actions: {
    async createCategory(payload: CreationPayload) {
      this.loading = true

      const { data, error } = await useCustomFetch<any>(
        'api/usuario/private/crearCategoria',
        {
          method: 'POST',
          body: JSON.stringify(payload)
        }
      )

      // Errorr Handling
      if (error.value) {
        useSnackbarStore().showSnackbar({
          title: 'Error',
          message: convertError(error.value),
          type: SnackbarType.ERROR
        })
        this.loading = false
        return { data, error: error.value }
      }
      // Success
      // Show success snackbar
      useSnackbarStore().showSnackbar({
        title: 'Creacion Exitosa',
        message: `Categoria Creada Exitosamente`,
        type: SnackbarType.SUCCESS
      })
      // Return the data and error
      this.loading = false
      return { data, error: false }
    }
  }
})
