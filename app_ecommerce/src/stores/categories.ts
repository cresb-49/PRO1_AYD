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
    async fetchAllCategories() {
      this.loading = true
      
      console.log('intenta obtener las categorias');

      const { data, error } = await useCustomFetch<any>(
        'api/categorias/',
        {
          method: 'GET',
        }
      )

      console.log('intenta obtener las categorias');
      console.log('data');
      console.log(data);
      console.log('error');
      console.log(error);

      // Error Handling
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
      // Return the data and error
      this.loading = false
      return { data, error: false }
    },
    async createCategory(payload: CreationPayload) {
      this.loading = true

      const { data, error } = await useCustomFetch<any>(
        'api/categoria/private/crearCategoria',
        {
          method: 'POST',
          body: JSON.stringify(payload)
        }
      )

      // Error Handling
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
