import { useCustomFetch } from '@/composables/useCustomFetch'
import { defineStore } from 'pinia'
import { useSnackbarStore, SnackbarType } from './snackbar'
import type { User } from './regular-auth'

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

export const useUserStore = defineStore('users', {
  state: () => ({
    users: new Array<User>,
    loading: false,
    error: false
  }),
  actions: {
    async fetchAllUsers() {
      this.loading = true
      
      const { data, error } = await useCustomFetch<any>(
        'api/usuario/private/getUsuarios',
        {
          method: 'GET',
        }
      )

      this.users = data.value.data;

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
      console.log('usuarios')
      console.log(data.value.data)
      return { data, error: false }
    },
    async fetchUser(category_id: number) {
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
    async createUser(payload: CreationPayload) {
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
      
      await this.fetchAllUsers()
      // Return the data and error
      this.loading = false
      return { data, error: false }
    },
    async updateUser(payload: UpdatePayload) {
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
      
      await this.fetchAllUsers()
      // Return the data and error
      this.loading = false
      return { data, error: false }
    }
  }
})
