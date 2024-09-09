import { createApp } from 'vue'
import { createPinia } from 'pinia'
import 'vuetify/styles'
import '@mdi/font/css/materialdesignicons.css' // Ensure you are using css-loader
import piniaPluginPersistedState from "pinia-plugin-persistedstate";

import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'

import App from './App.vue'
import router from './router'
import { VDateInput } from 'vuetify/labs/components';

const vuetify = createVuetify({
    components: {
        ...components,
        VDateInput
    },
    directives,
})

const app = createApp(App)
const pinia = createPinia()

pinia.use(piniaPluginPersistedState)
app.use(pinia)
app.use(vuetify)
app.use(router)

app.mount('#app')
