<template>
  <main>
    <header class="mb-4">
      <h1>Personalizacion Tienda - Editar</h1>
      <v-btn prepend-icon="mdi-arrow-left" to="/admin/store" class="mt-3">
        Regresar
      </v-btn>
    </header>
    <section class="profile-edit-section">
      <StoreInfoForm></StoreInfoForm>
    </section>
    <section class="profile-edit-section">
      <PasswordProfileForm
        :src="user"
        :loading="loading"
        ref="passwordForm"
        @save-password="updateUserPassword($event)"
      />
    </section>
  </main>
</template>
<script setup lang="ts">
import { useRegularAuthStore, type UserUpdatePayload } from '@/stores/regular-auth';
import { ref } from 'vue';
import StoreInfoForm from '@/components/customization/StoreInfoForm.vue';

const regularAuthStore = useRegularAuthStore();
const {user, loading, updateProfile} = regularAuthStore;
const passwordForm = ref()

function updateUserPassword(userWithPassword: UserUpdatePayload) {
  passwordForm.value!.resetFields()
  updateProfile(userWithPassword)
}
</script>
<style lang="scss" scoped>
.profile-edit-section {
  margin-bottom: 1.5rem;
}
</style>
