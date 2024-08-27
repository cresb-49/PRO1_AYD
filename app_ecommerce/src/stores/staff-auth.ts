import { defineStore } from 'pinia'
import { useAuthStore } from './auth'
import { useCustomFetch } from '../composables/useCustomFetch'
import { useRouter } from 'vue-router'
import { SnackbarType, useSnackbarStore } from './snackbar'
import { convertError } from '@/utils/error-converter'
import { useCookies } from 'vue3-cookies'

type LoginPayload = {
  email: string
  password: string
}

export type Role = {
  id: number
  alias: string
  name: string
  description: string
  created_at: Date
  updated_at: Date
}

export type Staff = {
  id: number
  nombres: string
  apellidos: string
  email: string
  roles: Role[]
  created_at: Date
  updated_at: Date
}

type LoginResponse = {
  staff: Staff
  token: string
}

export const useStaffAuthStore = defineStore('staff-auth', {
  state: () => ({
    authenticated: false,
    loading: false,
    // error: null as any | {} as any | null,
    user: null as Staff | null,
    staffRoles: [] as any[]
  }),
  persist: true,
  actions: {
    async loginStaff(payload: LoginPayload) {
      this.loading = true
      // this.error = null
      const router = useRouter()
      // Fetch the data from the API
      const { data, error } = await useCustomFetch<LoginResponse>(
        '/staff-auth/login',
        {
          method: 'POST',
          body: payload
        }
      )
      if (error.value) {
        if (error.value.data) {
          useSnackbarStore().showSnackbar({
            title: 'Error',
            message: convertError(error.value.data.message),
            type: SnackbarType.ERROR
          })
          error.value = null
          this.loading = false
          return
        }
        if (error.value.cause) {
          useSnackbarStore().showSnackbar({
            title: 'Error',
            message: convertError(error.value!.message),
            type: SnackbarType.ERROR
          })
          error.value = null
          this.loading = false
          return
        }
        /*
        Note: Set the error value to null to bypass nuxt's de-duplication (key based) mechanism
        and be able to make the request again
        */
      }
      // Success
      // Set cookies, user and role
      useCookies().cookies.set('user-token', data?.value?.token!);
      useCookies().cookies.set('roleuser', 'staff');
      // Set the user in the store
      this.user = data?.value?.staff ?? null
      // Set the authenticated flag
      this.authenticated = true
      // Set the staff roles
      this.staffRoles = data?.value?.staff?.roles ?? []
      // Set the token and role in the auth store
      const authStore = useAuthStore()
      authStore.role = 'staff'
      authStore.token = data?.value?.token ?? ''
      authStore.isAuthenticated = true
      // Redirect to the dashboard
      router.push('/admin/home')
      // Return the data and error
      this.loading = false
      return { data, error: false }
    },
    async myProfile() {
      this.loading = true
      const { data } = await useCustomFetch<Staff>('/staff-auth/me')

      if (data.value) {
        this.user = data?.value ?? null
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
    clear() {
      this.user = null
      this.authenticated = false
      // this.error = null
    }
  }
})
