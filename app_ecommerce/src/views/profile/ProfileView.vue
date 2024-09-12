<template>
  <main>
    <header class="mb-4">
      <h1>Mi perfil</h1>
      <v-div v-if="role == 'regular'">
      <h2 >Tienda</h2>
      <v-row>
        <v-col cols="12" xs="8" sm="4" md="4" lg="4">
          <menu-option-card
          title="Compras"
          subtitle="Ver mis compras"
          path="/ventas"
          ></menu-option-card>
        </v-col>
      </v-row>
      <v-divider class="mt-4 mb-6"></v-divider>
      </v-div>
      <v-btn prepend-icon="mdi-pencil-outline" to="/perfil/edit" class="mt-3"> Editar </v-btn>
    </header>
    <section class="profile-edit-section">
      <PersonalInfoDetails :src="user" />
    </section>
  </main>
</template>

<script setup lang="ts">
import { storeToRefs } from 'pinia'
import PersonalInfoDetails from '../../components/profile/details/PersonalInfoDetails.vue'
import MenuOptionCard from "../../components/partials/MenuOptionCard.vue";
import { useAuthStore } from '@/stores/auth'
import { useRegularAuthStore } from '@/stores/regular-auth'

const authStore = useAuthStore()
authStore.fetchUser()
const regularAuthStore = useRegularAuthStore()
const { user } = storeToRefs(regularAuthStore)
const { role, logout } = authStore;
</script>

<style lang="scss" scoped>
.profile-edit-section {
  margin-bottom: 1.5rem;
}
</style>
