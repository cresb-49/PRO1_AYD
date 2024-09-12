import { defineStore } from 'pinia'
import { useAuthStore } from './auth'
import { SnackbarType, useSnackbarStore } from './snackbar'
import { useCustomFetch } from '@/composables/useCustomFetch'
import { convertError } from '@/utils/error-converter'
import { useCookies } from 'vue3-cookies'
import { jwtDecode } from 'jwt-decode'

export enum UserRole {
  AYUDANTE = 'AYUDANTE',
  ADMIN = 'ADMIN',
  USUARIO = 'USUARIO'
}

type UserPayload = {
  email?: string
  password?: string
  twoFactorCode?: string
}

export type ForgotPasswordPayload = {
  correoElectronico: string
}

export type ChangePasswordTokenPayload = {
  nuevaPassword: string
  codigo: string
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

export type SignUpAyudante = {
  usuario: SignupPayload
  permisos: any[]
}

export type User = {
  id: number
  nombres: string
  apellidos: string
  email: string
  nit: string
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
    cleanForm: false,
    error: null as any | string[] | null,
    user: null as User | null
  }),
  persist: true,
  actions: {
    async loginUser(payload: UserPayload) {
      const authStore = useAuthStore()
      this.loading = true
      this.error = null

      const hasTwoFactorCode = payload.twoFactorCode !== undefined
      const path = hasTwoFactorCode
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
      const userHasTwoFactor = data?.value?.data?.hasTwoFactorCode ?? false
      if (userHasTwoFactor && !hasTwoFactorCode) {
        this.authenticated = false
        useSnackbarStore().showSnackbar({
          title: 'Se necesita autenticación de dos factores',
          message: `Ingresa tu token ${this.user?.nombres} ${this.user?.apellidos}`,
          type: SnackbarType.MESSAGE
        })
        // Return the data and error
        this.loading = false
        return { data, error: false, twoFactor: true }
      } else {
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
        return { data, error: false, twoFactor: false }
      }
    },
    async signupUser(
      payload: SignupPayload | SignUpAyudante,
      typeUser: UserRole,
      autoLogin = true
    ) {
      const authStore = useAuthStore()
      this.cleanForm = false
      // const { nombres, apellidos, email, password } = payload
      // const newPayload = {
      //   nombres: nombres,
      //   apellidos: apellidos,
      //   email: email,
      //   password: password
      // }
      this.loading = true
      this.error = null
      let path = 'api/usuario/public/crearUsuario'
      switch (typeUser) {
        case UserRole.USUARIO:
          path = 'api/usuario/public/crearUsuario'
          break
        case UserRole.ADMIN:
          path = 'api/usuario/private/crearAdministrador'
          break
        case UserRole.AYUDANTE:
          path = 'api/usuario/private/crearAyudante'
          break
      }
      // Fetch the data from the API
      const { data, error } = await useCustomFetch<LoginResponse>(path, {
        method: 'POST',
        body: JSON.stringify(payload)
      })
      // Error handling
      if (error.value) {
        useSnackbarStore().showSnackbar({
          title: 'Error',
          message: error.value,
          type: SnackbarType.ERROR
        })
        this.loading = false
        this.cleanForm = false
        return { data, error: error.value }
      }
      // Success
      // Set the user in the store
      if (autoLogin) {
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
      }
      // Return the data and error
      this.loading = false
      this.cleanForm = true
      return { data, error: false }
    },
    async myProfile() {
      this.loading = true
      // const snackbarStore = useSnackbarStore()

      const { data } = await useCustomFetch<User>(
        `api/usuario/private/all/perfil/${this.user!.id}`,
        {
          method: 'GET'
        }
      )
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
    async sendForgotPasswordEmail(payload: ForgotPasswordPayload) {
      this.loading = true

      const path = 'api/usuario/public/recuperarPasswordMail'

      const { data, error } = await useCustomFetch<any>(path, {
        method: 'POST',
        body: JSON.stringify(payload)
      })
      if (error.value) {
        console.log(error.value)
        this.loading = false
        return
      }
      this.loading = false
      useSnackbarStore().showSnackbar({
        title: 'Correo enviado',
        message: 'Se ha enviado un correo para recuperar tu contraseña',
        type: SnackbarType.SUCCESS
      })
    },
    async changePasswordWithToken(payload: ChangePasswordTokenPayload) {
      this.loading = true

      const path = 'api/usuario/public/recuperarPassword'

      const { data, error } = await useCustomFetch<any>(path, {
        method: 'PATCH',
        body: JSON.stringify(payload)
      })
      if (error.value) {
        console.log(error.value)
        useSnackbarStore().showSnackbar({
          title: 'Error',
          message: error.value,
          type: SnackbarType.ERROR
        })
        this.loading = false
        return { data, error: true }
      }
      this.loading = false
      useSnackbarStore().showSnackbar({
        title: 'Contraseña cambiada',
        message: 'Tu contraseña ha sido cambiada exitosamente',
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
    },
    async setEstadoDosPasos(estado: boolean, id_usuario: number) {
      this.loading = true

      const path = 'api/usuario/private/all/cambiarTwoFactor'

      const { data, error } = await useCustomFetch<any>(path, {
        method: 'PATCH',
        body: JSON.stringify({ id: id_usuario, activacion: estado })
      })
      if (error.value) {
        console.log(error.value)
        useSnackbarStore().showSnackbar({
          title: 'Error',
          message: error.value,
          type: SnackbarType.ERROR
        })
        this.loading = false
        return { data, error: true }
      }
      this.loading = false
      useSnackbarStore().showSnackbar({
        title: estado
          ? 'Verificación de dos pasos activada'
          : 'Verificación de dos pasos desactivada',
        message: estado
          ? 'Se ha activado la verificación de dos pasos'
          : 'Se ha desactivado la verificación de dos pasos',
        type: SnackbarType.SUCCESS
      })
      return { data, error: false }
    }
    //Here action
  }
})
