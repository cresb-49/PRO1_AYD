import { useCustomFetch } from '@/composables/useCustomFetch'
import { defineStore } from 'pinia'
import { useSnackbarStore, SnackbarType } from './snackbar'
import { convertError } from '@/utils/error-converter'

export type CreationPayload = {
  nombre: string,
  padre?: number
}

export type UpdatePayload = {
  id: number,
  nombre: string,
  padre?: number
}
 
export type Category = {
  id: number,
  nombre: string,
  padre?: number
}

export const useCategoryStore = defineStore('categories', {
  state: () => ({
    categories: new Array<Category>,
    loading: false,
    error: false
  }),
  actions: {
    async fetchAllCategories() {
      this.loading = true
      
      const { data, error } = await useCustomFetch<any>(
        'api/categoria/public/getCategorias',
        {
          method: 'GET',
        }
      )

      
      this.categories = data.value.data;

      // Error Handling
      if (error.value) {
        useSnackbarStore().showSnackbar({
          title: 'Error',
          message: error.value,
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
    async fetchCategory(category_id: number) {
      this.loading = true
      
      const { data, error } = await useCustomFetch<any>(
        `api/categoria/${category_id}`,
        {
          method: 'GET',
        }
      )

      // Error Handling
      if (error.value) {
        useSnackbarStore().showSnackbar({
          title: 'Error',
          message: error.value,
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
      console.log('payload actual');
      console.log(payload);
      this.loading = true

      const { data, error } = await useCustomFetch<any>(
        'api/categoria/protected/crearCategoria',
        {
          method: 'POST',
          body: JSON.stringify(payload)
        }
      )

      // Error Handling
      if (error.value) {
        useSnackbarStore().showSnackbar({
          title: 'Error',
          message: error.value,
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
      
      await this.fetchAllCategories()
      // Return the data and error
      this.loading = false
      return { data, error: false }
    },
    async updateCategory(payload: UpdatePayload) {
      this.loading = true

      const { data, error } = await useCustomFetch<any>(
        'api/categoria/protected/updateCategoria',
        {
          method: 'PATCH',
          body: JSON.stringify(payload)
        }
      )

      // Error Handling
      if (error.value) {
        useSnackbarStore().showSnackbar({
          title: 'Error',
          message: error.value,
          type: SnackbarType.ERROR
        })
        this.loading = false
        return { data, error: error.value }
      }
      // Success
      // Show success snackbar
      useSnackbarStore().showSnackbar({
        title: 'Actualizacion Exitosa',
        message: `Categoria Actualizada Exitosamente`,
        type: SnackbarType.SUCCESS
      })
      
      await this.fetchAllCategories()
      // Return the data and error
      this.loading = false
      return { data, error: false }
    }
  }
})
