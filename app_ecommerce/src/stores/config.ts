import { defineStore } from 'pinia'
import { useCookies } from 'vue3-cookies'

export const useConfigsStore = defineStore('configs', {
  state: () => ({
    theme: 'light',
    themeBoolean: false
  }),
  actions: {
    initTheme() {
      // Here we use cookies to persist the theme
      let themeCookie = useCookies().cookies.get('theme');
      if (!themeCookie) {
        // If there's no cookie, we set the theme to the default
        this.theme = 'light'
        // this.themeBoolean = false
        themeCookie = 'light'
        useCookies().cookies.set('theme', this.theme);
      } else {
        // Otherwise we set the theme to the cookie value
        this.theme = themeCookie
        // this.themeBoolean = themeCookie.value === 'dark'
      }
    },
    switchTheme() {
      this.theme = this.theme === 'light' ? 'dark' : 'light'
      useCookies().cookies.set('theme', this.theme);
    },
    changeTheme(theme?: string | null) {
      if (!theme) return
      this.theme = theme
      useCookies().cookies.set('theme', theme);
    }
  }
})
