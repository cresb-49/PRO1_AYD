import { useCustomFetch } from '@/composables/useCustomFetch'
import { defineStore } from 'pinia'
import { useSnackbarStore, SnackbarType } from './snackbar'
import type { User } from './regular-auth'

export type CreationPayload = {
  nombre: string
  padre?: number
}

export type UpdateUserPayload = {
  id: number
  nombres: string
  apellidos: string
  email: string
  nit?: string
}

export type UpdatePermisosPayload = {
  idUsuario: number
  permisos: Permiso[]
}

export type Category = {
  id: number
  nombre: string
  padre?: number
}

export type Rol = {
  id: number
  createdAt: string
  nombre: string
}

export type UsuarioRol = {
  id: number
  createdAt: string
  rol: Rol
}

export type Permiso = {
  id: number
  createdAt?: string
  nombre: string
  ruta: string
}

export type UsuarioPermiso = {
  id: number
  createdAt: string
  permiso: Permiso
}

export type UserByPermiso = {
  id: number
  createdAt: string
  nombres: string
  apellidos: string
  email: string
  nit: string
  roles: UsuarioRol[]
  permisos: UsuarioPermiso[]
}

export const useUserStore = defineStore('users', {
  state: () => ({
    users: new Array<User>(),
    permisos: new Array<Permiso>(),
    loading: false,
    error: false
  }),
  actions: {
    async fetchAllUsers() {
      this.loading = true

      const { data, error } = await useCustomFetch<any>('api/usuario/private/getUsuarios', {
        method: 'GET'
      })

      this.users = data.value.data

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
    async fetchUser(user_id: number) {
      this.loading = true
      const { data, error } = await useCustomFetch<any>(
        `api/usuario/private/all/perfil/${user_id}`,
        {
          method: 'GET'
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
      console.log('payload actual')
      console.log(payload)
      this.loading = true

      const { data, error } = await useCustomFetch<any>('api/categoria/protected/crearCategoria', {
        method: 'POST',
        body: JSON.stringify(payload)
      })

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
    async updateUser(payload: UpdateUserPayload, updateAllUsers: boolean = true) {
      this.loading = true

      const { data, error } = await useCustomFetch<any>('api/usuario/private/all/updateUsuario', {
        method: 'PATCH',
        body: JSON.stringify(payload)
      })
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
        message: `Usuario Actualizado Exitosamente`,
        type: SnackbarType.SUCCESS
      })
      if (updateAllUsers) {
        await this.fetchAllUsers()
      }
      // Return the data and error
      this.loading = false
      return { data, error: false }
    },
    async updatePermisosUser(payload: UpdatePermisosPayload) {
      this.loading = true
      const { data, error } = await useCustomFetch<any>('api/usuario/private/actualizarPermisos', {
        method: 'PATCH',
        body: JSON.stringify(payload)
      })
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
        message: `Permisos Actualizados Exitosamente`,
        type: SnackbarType.SUCCESS
      })
      return { data, error: false }
    },
    async fetchAllPermisos() {
      this.loading = true
      const { data, error } = await useCustomFetch<any>('api/permiso/private/getPermisos', {
        method: 'GET'
      })
      this.permisos = data.value.data
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
    async deleteUser(user_id: number) {
      this.loading = true
      const { data, error } = await useCustomFetch<any>(
        `api/usuario/private/eliminarUsuario/${user_id}`,
        {
          method: 'DELETE'
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
        title: 'Eliminacion Exitosa',
        message: `Usuario Eliminado Exitosamente`,
        type: SnackbarType.SUCCESS
      })
      // Return the data and error
      this.loading = false
      return { data, error: false }
    }
  }
})
