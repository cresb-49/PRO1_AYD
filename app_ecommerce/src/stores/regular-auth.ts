import { defineStore } from 'pinia'
import { useAuthStore } from './auth'
import { SnackbarType, useSnackbarStore } from './snackbar'
import { useCustomFetch } from '@/composables/useCustomFetch'
import { convertError } from '@/utils/error-converter'
import { useCookies } from 'vue3-cookies'
import { jwtDecode } from 'jwt-decode'

type UserPayload = {
  email?: string
  password?: string
  twoFactorCode?: string
}

export type UserUpdatePayload = {
  nombres?: string
  apellidos?: string
  email?: string
  password?: string
}

export type SignupPayload = {
  nombres: string
  apellidos: string
  email: string
  password: string
}

export type User = {
  id: number
  nombres: string
  apellidos: string
  email: string
  created_at: Date
  updated_at: Date
  roles: RoleResponse[]
}

export type RoleResponse = {
  rol: Role
}

export type Role = {
  id: number
  nombre: string
}

export type LoginResponse = {
  usuario: User
  jwt: string
}

export const useRegularAuthStore = defineStore('regular-auth', {
  state: () => ({
    authenticated: false,
    loading: false,
    error: null as any | string[] | null,
    user: null as User | null
  }),
  persist: true,
  actions: {
    async loginUser(payload: UserPayload) {
      const authStore = useAuthStore()
      this.loading = true
      this.error = null

      console.log(payload)
      const path =
        payload.twoFactorCode !== undefined
          ? 'api/usuario/public/validateTwoFactorToken'
          : 'api/usuario/public/login'

      // Fetch the data from the API
      const { data, error } = await useCustomFetch<any>(path, {
        method: 'POST',
        body: JSON.stringify(payload)
      })

      // Errorr Handling
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
      // Set the user in the store
      this.user = data?.value?.data?.usuario ?? null
      this.authenticated = true

      let roleuser = ''
      switch (this.user?.roles[0].rol.id) {
        case 1:
          roleuser = 'regular'
          break
        case 2:
          roleuser = 'admin'
          break
        case 3:
          roleuser = 'helper'
          break
        default:
          roleuser = 'regular'
          break
      }
      // Set cookies, user and role
      useCookies().cookies.set('user-token', data?.value?.data?.jwt)
      useCookies().cookies.set('roleuser', roleuser)
      // Set the token and role in the auth store
      authStore.login({ role: roleuser, token: data?.value?.data?.jwt ?? '' })
      // Show success snackbar
      useSnackbarStore().showSnackbar({
        title: 'Session iniciada',
        message: `Bienvenid@ ${this.user?.nombres} ${this.user?.apellidos}`,
        type: SnackbarType.SUCCESS
      })
      // Return the data and error
      this.loading = false
      return { data, error: false }
    },
    async signupUser(payload: SignupPayload) {
      const authStore = useAuthStore()
      const { nombres, apellidos, email, password } = payload
      const newPayload = {
        nombres: nombres,
        apellidos: apellidos,
        email: email,
        password: password
      }
      this.loading = true
      this.error = null
      // Fetch the data from the API
      const { data, error } = await useCustomFetch<LoginResponse>(
        'api/usuario/public/crearUsuario',
        {
          method: 'POST',
          body: JSON.stringify(newPayload)
        }
      )
      // Error handling
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
      // Set the user in the store
      this.user = data?.value?.data?.usuario ?? null
      this.authenticated = true

      let roleuser = ''
      switch (this.user?.roles[0].rol.id) {
        case 1:
          roleuser = 'regular'
          break
        case 2:
          roleuser = 'admin'
          break
        case 3:
          roleuser = 'helper'
          break
        default:
          roleuser = 'regular'
          break
      }
      // Set cookies, user and role
      useCookies().cookies.set('user-token', data?.value?.data?.jwt)
      useCookies().cookies.set('roleuser', roleuser)
      // Set the token and role in the auth store
      authStore.login({ role: roleuser, token: data?.value?.data?.jwt ?? '' })
      // Return the data and error
      this.loading = false
      return { data, error: false }
    },
    async myProfile() {
      this.loading = true
      // const snackbarStore = useSnackbarStore()

      const { data } = await useCustomFetch<User>(`api/usuario/private/perfil/${this.user!.id}`, {
        method: 'GET'
      })
      if (data.value) {
        this.user = data?.value?.data
      } else {
        useSnackbarStore().showSnackbar({
          title: 'Error de sesión',
          message: 'No se ha podido recuperar tu sesión, por favor vuelve a intentar más tarde',
          type: SnackbarType.ERROR
        })
      }
      this.loading = false
    },
    async updateProfile(payload: UserUpdatePayload) {
      this.loading = true
      const user_id = this.user!.id

      //Si se envia el password se envia solo esta a actualizar, caso contrario se actualizan los demas campos del usuario
      const url_api_update = payload.password
        ? 'api/usuario/private/all/cambioPassword'
        : 'api/usuario/private/all/updateUsuario'
      const { data, error } = await useCustomFetch<User>(url_api_update, {
        method: 'PATCH',
        body: JSON.stringify({ id: user_id, ...payload })
      })
      if (error.value) {
        console.log(error.value)
        this.loading = false
        return
      }
      if (data.value) {
        this.myProfile()
      }
      this.loading = false
      useSnackbarStore().showSnackbar({
        title: 'Actualizacion Exitosa',
        message: payload.password
          ? `Password actualizada exitosamente`
          : `Tu perfil se ha actualizado exitosamente`,
        type: SnackbarType.SUCCESS
      })
      return { data, error: false }
    },
    clearError() {
      this.error = null
    },
    clear() {
      this.user = null
      this.authenticated = false
      this.error = null
    }
  }
})
