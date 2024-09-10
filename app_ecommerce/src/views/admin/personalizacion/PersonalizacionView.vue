<template>
  <main>
    <header class="mb-4">
      <h1>Personalizar Tienda</h1>
      <v-btn
        prepend-icon="mdi-pencil-outline"
        to="/admin/store/edit"
        class="mt-3"
      >
        Editar
      </v-btn>
    </header>
    <section class="profile-edit-section">
      <StoreInfoDetails :src="user" />
    </section>
  </main>
</template>

<script setup lang="ts">
import { storeToRefs } from 'pinia'
import StoreInfoDetails from '../../../components/customization/StoreInfoDetails.vue'
import { useAuthStore } from '@/stores/auth';
import { useRegularAuthStore } from '@/stores/regular-auth';

const authStore = useAuthStore()
authStore.fetchUser()
const regularAuthStore = useRegularAuthStore()
const { user } = storeToRefs(regularAuthStore)
</script>

<style lang="scss" scoped>
.profile-edit-section {
  margin-bottom: 1.5rem;
}
</style>
