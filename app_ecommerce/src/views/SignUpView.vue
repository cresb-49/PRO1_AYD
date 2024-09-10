<template>
  <section class="login-page">
    <v-row justify="center" no-gutters>
      <v-col cols="11" sm="8" md="8" lg="5" xl="4">
        <SignupForm :loading="loading" :error="error" @signup="signUp($event)" />
      </v-col>
    </v-row>
  </section>
</template>
<script setup lang="ts">
import { storeToRefs } from 'pinia'
import { useRegularAuthStore, UserRole, type SignupPayload } from '../stores/regular-auth'
import SignupForm from '../components/forms/accounts/SignupForm.vue'
import { useRouter } from 'vue-router'

const regularAuthStore = useRegularAuthStore()
const { loading, error } = storeToRefs(regularAuthStore)
const { signupUser } = regularAuthStore
const router = useRouter()

async function signUp(payload: SignupPayload) {
  const { error } = await signupUser(payload, UserRole.USUARIO)
  if (error === false) {
    router.push('/')
  }
}
</script>
<style lang="scss" scoped>
.login-page {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  width: 100%;
}
</style>
