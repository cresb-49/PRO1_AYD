<template>
  <v-app :theme="theme">
    <v-app-bar class="app-bar" :elevation="elevation">
      <!--
      <v-btn icon="mdi-menu" @click="drawer = !drawer" />
      -->
      <v-app-bar-title class="mt-n1">
        <router-link to="/" style="text-decoration: none; color:inherit">
          <v-row>
            <v-col cols="1" xs="1" sm="5" md="5"></v-col>
            <v-col cols="1">
              <v-img v-if="logo" :src="logo" max-height="30" />
            </v-col>
            <v-divider v-if="logo" vertical></v-divider>
            <v-col cols="1">
              <h4>{{ name }}</h4>
            </v-col>
            <v-col cols="1" xs="1" sm="1" md="4"></v-col>
          </v-row>
        </router-link>
      </v-app-bar-title>
      <div v-if="!user" class="app-bar__user-info">
        <!-- Dashboard action (regular and admin) -->
        <v-tooltip text="Iniciar sesión" location="bottom">
          <template #activator="{ props }">
            <v-btn to="/login" v-bind="props" class="app-bar__avatar" icon>
              <v-icon> mdi-account-circle-outline </v-icon>
            </v-btn>
          </template>
        </v-tooltip>
      </div>
      <div v-if="user && role === 'regular'" class="app-bar__user-info">
        <v-tooltip text="Carrito" location="bottom">
          <template #activator="{ props }">
            <v-avatar v-bind="props" class="app-bar__avatar text-accent-4" @click="$router.push('/carrito')">
              <v-icon> mdi-cart-outline </v-icon>
            </v-avatar>
          </template>
        </v-tooltip>
        <!-- If user is logged in -->
        <v-tooltip text="Mi perfil" location="bottom">
          <template #activator="{ props }">
            <v-avatar v-bind="props" class="app-bar__avatar text-accent-4" @click="$router.push('/perfil')">
              <strong>
                {{ loggedUser?.nombres[0]
                }}{{ loggedUser?.apellidos[0] }}
              </strong>
            </v-avatar>
          </template>
        </v-tooltip>
        <v-tooltip text="Cerrar sesión" location="bottom">
          <template #activator="{ props }">
            <v-btn icon v-bind="props" @click="logoutSession">
              <v-icon> mdi-logout-variant </v-icon>
            </v-btn>
          </template>
        </v-tooltip>
      </div>
      <div v-if="user && role !== 'regular'" class="app-bar__user-info">
        <!-- Dashboard action (regular and admin) -->
        <v-tooltip text="Mi perfil" location="bottom">
          <template #activator="{ props }">
            <v-avatar v-bind="props" class="app-bar__avatar text-accent-4" @click="$router.push('/perfil')">
              <strong>
                {{ loggedUser?.nombres[0]
                }}{{ loggedUser?.apellidos[0] }}
              </strong>
            </v-avatar>
          </template>
        </v-tooltip>
        <v-tooltip text="Administración" location="bottom">
          <template #activator="{ props }">
            <v-avatar v-bind="props" class="app-bar__avatar text-accent-4" @click="$router.push('/admin/')">
              <v-icon> mdi-view-dashboard-outline </v-icon>
            </v-avatar>
          </template>
        </v-tooltip>
        <v-tooltip text="Cerrar sesión" location="bottom">
          <template #activator="{ props }">
            <v-btn icon v-bind="props" @click="logoutSession">
              <v-icon> mdi-logout-variant </v-icon>
            </v-btn>
          </template>
        </v-tooltip>
      </div>
    </v-app-bar>

    <!--
    <v-navigation-drawer v-model="drawer" disable-resize-watcher width="350">
      <SidebarNavigator />
    </v-navigation-drawer>
    -->

    <v-main class="d-flex flex-column">
      <v-container class="page-v-container" style="flex-grow: 1">
        <slot />
      </v-container>

      <v-footer style="flex-grow: 0" class="pa-4">
        <v-col class="text-center" cols="12">
          <strong>AyD</strong> — {{ new Date().getFullYear() }}
        </v-col>
      </v-footer>
    </v-main>
    <GeneralSnackbar />
  </v-app>
</template>
<script setup lang="ts">
import { useConfigsStore } from '../stores/config'
import { useAuthStore } from '../stores/auth'
import { type User } from '../stores/regular-auth'
import { type Staff } from '../stores/staff-auth'
import GeneralSnackbar from '../components/partials/GeneralSnackbar.vue'
import { computed, ref } from 'vue'
import { useRouter } from 'vue-router'
import { storeToRefs } from 'pinia'

const drawer = ref(false);
const { name, logo } = useConfigsStore()
const authStore = useAuthStore();
const { role, user, logout } = authStore;
const router = useRouter();

const elevation = computed(() => {
  return drawer.value ? 2 : 0
})

const loggedUser = computed(() => {
  return user as User
})

function logoutSession() {
  logout()
  router.push('/')
  router.go(0)
}

</script>
<style lang="scss" scoped>
.main-content {
  &__page {
    padding: 0 !important;
    height: 100%;
    width: 100%;
    min-height: 100vh;
  }
}

@import '@/assets/styles/main.scss';
</style>