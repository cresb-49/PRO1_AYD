<template>
  <main>
    <header class="mb-4">
      <h1>Mi perfil - Editar</h1>
      <v-btn prepend-icon="mdi-arrow-left" to="/perfil" class="mt-3">
        Regresar
      </v-btn>
    </header>
    <section class="profile-edit-section">
      <PersonalInfoForm
        :src="user"
        :loading="loading"
        @save="updateProfile($event)"
      />
    </section>
    <section class="profile-edit-section">
      <PasswordProfileForm
        :src="user"
        :loading="loading"
        @save-password="updateUserPassword($event)"
      />
    </section>
  </main>
</template>
<script setup lang="ts">
import PersonalInfoForm from '../../components/profile/PersonalInfoForm.vue'
import PasswordProfileForm from '../../components/profile/PasswordProfileForm.vue'
import { useRegularAuthStore, type UserUpdatePayload } from '@/stores/regular-auth';

const regularAuthStore = useRegularAuthStore();
const {user, authenticated, loading, myProfile, updateProfile} = regularAuthStore;

function updateUserProfile(user: UserUpdatePayload) {
  updateProfile(user)
}

function updateUserPassword(userWithPassword: UserUpdatePayload) {
  updateProfile(userWithPassword)
}
</script>
<style lang="scss" scoped>
.profile-edit-section {
  margin-bottom: 1.5rem;
}
</style>
