import { jwtDecode } from 'jwt-decode'
import { defineStore } from 'pinia'
import { useRegularAuthStore } from './regular-auth'
import { useStaffAuthStore } from './staff-auth'
import { useRouter } from "vue-router";
import { useCookies } from 'vue3-cookies';
import { useCartStore } from './cart';

type UserJwt = {
  id: number
  email: string
  created_at: Date
  updated_at: Date
  iat: number
  exp: number
}
type LoginParameters = {
  token: string,
  role: string
}

export const useAuthStore = defineStore('auth', {
  state: () => ({
    role: '' as string | null,
    token: '' as string | null,
    isAuthenticated: false
  }),
  persist: true,
  actions: {
    login(parameters: LoginParameters) {
      const {token, role} = parameters
      this.token = token
      this.role = role
      this.isAuthenticated = true
    },
    logout() {
      const regularAuthStore = useRegularAuthStore()
      const staffAuthStore = useStaffAuthStore()
      const cartStore = useCartStore()
      useCookies().cookies.remove('user-token');
      useCookies().cookies.remove('roleuser');
      // Clear the user in the respective store
      if (regularAuthStore.user) {
        regularAuthStore.clear()
        cartStore.$reset()
      } else {
        staffAuthStore.clear()
      }
      this.role = null
      this.token = null
      this.isAuthenticated = false
      return true;
    },
    async fetchAuth() {
      // const role = useCookie('cicsapp-roleuser')
      // this.role = role.value ?? null
      const tokenCookie = useCookies().cookies.get('user-token')
      this.token = tokenCookie ?? null
      if (tokenCookie) {
        const decoded = jwtDecode<UserJwt>(tokenCookie)
        this.role = decoded.profile_id ? 'regular' : 'staff'
        this.isAuthenticated = true
        if (this.role === 'staff') {
          const staffAuthStore = useStaffAuthStore()
          staffAuthStore.authenticated = true
          await staffAuthStore.myProfile()
        } else {
          const regularAuthStore = useRegularAuthStore()
          regularAuthStore.authenticated = true
          await regularAuthStore.myProfile()
        }
      }
    },
    async fetchUser() {
      try {
        const role = useCookies().cookies.get('roleuser')
        if (role === 'staff') {
          const staffAuthStore = useStaffAuthStore()
          await staffAuthStore.myProfile()
        } else {
          const regularAuthStore = useRegularAuthStore()
          await regularAuthStore.myProfile()
        }
      } catch (error) {
        console.log(error)
      }
    }
  },
  getters: {
    getRole: (state) => state.role,
    getToken: (state) => state.token,
    user: (state) => {
      if (state.role === 'regular') {
        const regularAuthStore = useRegularAuthStore()
        return regularAuthStore.user
      } else {
        const regularAuthStore = useRegularAuthStore()
        return regularAuthStore.user
        /*
        const staffAuthStore = useStaffAuthStore()
        return staffAuthStore.user
        */
      }
    }
  }
})
