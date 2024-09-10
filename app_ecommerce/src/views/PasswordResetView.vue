<template>
  <section class="login-page">
    <v-row justify="center" no-gutters>
      <v-col cols="11" sm="8" md="8" lg="5" xl="4">
        <PasswordResetForm @sendEmail="sendEmail" />
      </v-col>
    </v-row>
  </section>
</template>

<!--Importar componentes hijos-->
<script setup lang="ts">
import { storeToRefs } from 'pinia'
import { useRegularAuthStore } from '@/stores/regular-auth'
import PasswordResetForm from '@/components/forms/accounts/PasswordResetForm.vue'
import { useRouter } from 'vue-router'

const regularAuthStore = useRegularAuthStore()
const { loading, error } = storeToRefs(regularAuthStore)
const { sendForgotPasswordEmail } = regularAuthStore
const router = useRouter()

async function sendEmail(params: { correoElectronico: string }) {
  await sendForgotPasswordEmail(params)
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
