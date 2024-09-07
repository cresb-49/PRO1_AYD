<template>
  <section class="login-page">
    <v-row justify="center" no-gutters>
      <v-col cols="11" sm="8" md="8" lg="5" xl="4">
        <TwoFactorForm
          :loading="loading"
          :admin="false"
          :show-signup="false"
          @login="login($event)"
        />
      </v-col>
    </v-row>
  </section>
</template>

<script setup lang="ts">
import { storeToRefs } from 'pinia'
import { useRegularAuthStore } from '@/stores/regular-auth'
import TwoFactorForm from '@/components/forms/accounts/TwoFactorForm.vue'
import { useRouter } from 'vue-router'

const regularAuthStore = useRegularAuthStore()
const { loading, error } = storeToRefs(regularAuthStore)
const { loginUser } = regularAuthStore
const router = useRouter()

async function login(credentials: { twoFactorCode: string }) {
  const { error } = await loginUser(credentials)
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