import { defineStore } from 'pinia'
import { useAuthStore } from './auth'
import { SnackbarType, useSnackbarStore } from './snackbar'
import { useCustomFetch } from '@/composables/useCustomFetch'
import { convertError } from '@/utils/error-converter'
import { useCookies } from 'vue3-cookies'

type UserPayload = {
  email: string
  password: string
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

export type Profile = {
  id: number
  nombres: string
  apellidos: string
  created_at: Date
  updated_at: Date
}

export type User = {
  id: number
  nombres: string
  apellidos: string
  email: string
  created_at: Date
  updated_at: Date
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
      // Fetch the data from the API

      const { data, error } = await useCustomFetch<any>(
        'api/usuario/public/login',
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
      // Set cookies, user and role
      useCookies().cookies.set('user-token', data?.value?.data?.jwt);
      useCookies().cookies.set('roleuser', 'regular');
      // Set the user in the store
      this.user = data?.value?.data?.usuario ?? null
      this.authenticated = true
      // Set the token and role in the auth store
      authStore.login({role: 'regular', token: data?.value?.data?.jwt ?? ''})
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
          message: convertError(error.value),
          type: SnackbarType.ERROR
        })
        this.loading = false
        return { data, error: error.value }
      }
      // Success
      // Set cookies, user and role
      useCookies().cookies.set('user-token', data?.value?.data?.jwt);
      useCookies().cookies.set('roleuser', 'regular');
      // Set the user in the store
      this.user = data?.value?.data?.usuario ?? null
      this.authenticated = true
      // Set the token and role in the auth store
      authStore.login({role: 'regular', token: data?.value?.data?.jwt ?? ''})
      // Return the data and error
      this.loading = false
      return { data, error: false }
    },
    async myProfile() {
      this.loading = true
      // const snackbarStore = useSnackbarStore()

      const { data } = await useCustomFetch<User>('/auth/me')
      if (data.value) {
        this.user = data?.value
      } else {
        useSnackbarStore().showSnackbar({
          title: 'Error de sesión',
          message:
            'No se ha podido recuperar tu sesión, por favor vuelve a intentar más tarde',
          type: SnackbarType.ERROR
        })
      }
      this.loading = false
    },
    async updateProfile(payload: UserUpdatePayload) {
      this.loading = true
      const { data, error } = await useCustomFetch<User>('/auth/me', {
        method: 'PUT',
        body: JSON.stringify(payload)
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
