<template>
  <main>
    <header class="mb-4">
      <h1 class="mb-4">Categorias - Agregar</h1>
      <v-btn prepend-icon="mdi-arrow-left" to="/admin/categorias" class="mt-3">
        Regresar
      </v-btn>
    </header>
    <section class="profile-edit-section">
      <NewCategoryForm/>
    </section>
  </main>
</template>

<script setup lang="ts">
import { storeToRefs } from 'pinia'
import { useAuthStore } from '@/stores/auth';
import { useRegularAuthStore } from '@/stores/regular-auth';
import NewCategoryForm from '@/components/forms/admin/categories/NewCategoryForm.vue';

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
