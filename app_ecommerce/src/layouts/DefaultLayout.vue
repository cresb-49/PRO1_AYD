<template>
    <v-app :theme="theme">
      <v-app-bar :elevation="elevation">
      <template #prepend>
        <v-btn
          :icon="drawer ? 'mdi-backburger' : 'mdi-menu'"
          @click="drawer = !drawer"
        />
      </template>

      <v-app-bar-title class="mt-n1">
        <strong>Dashboard </strong> - Ecommerce
      </v-app-bar-title>

      <v-tooltip text="Cerrar sesión" location="bottom">
        <template #activator="{ props }">
          <v-btn icon v-bind="props" @click="logout">
            <v-icon> mdi-logout-variant </v-icon>
          </v-btn>
          <span class="mr-4">
            UsuarioNombre UsuarioApellido
            <!--
            {{ user?.profile?.first_name }} {{ user?.profile?.last_name }}
            -->
          </span>
        </template>
      </v-tooltip>
    </v-app-bar>

      <v-main class="main-content">
        <v-container class="main-content__page">
          <slot />
        </v-container>
      </v-main>
      <GeneralSnackbar />
    </v-app>
</template>
<script lang="ts">
import { mapState } from 'pinia'
import { useConfigsStore } from '../stores/config'
import GeneralSnackbar from '../components/partials/GeneralSnackbar.vue'

export default {
  components: {
    GeneralSnackbar
  },
  data() {
    return {
      drawer: false,
      currentPage: ''
    }
  },
  computed: {
    ...mapState(useConfigsStore, ['theme'])
  }
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