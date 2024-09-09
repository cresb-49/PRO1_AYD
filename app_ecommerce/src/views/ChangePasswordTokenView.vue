<template>
  <section class="login-page">
    <v-row justify="center" no-gutters>
      <v-col cols="11" sm="8" md="8" lg="5" xl="4">
        <ChangePasswordForm @sendPasswordToken="sendPasswordToken" />
      </v-col>
    </v-row>
  </section>
</template>

<script setup lang="ts">
import { storeToRefs } from 'pinia'
import { useRegularAuthStore } from '@/stores/regular-auth'
import ChangePasswordForm from '@/components/forms/accounts/ChangePasswordForm.vue'
import { useRouter } from 'vue-router'
import { SnackbarType, useSnackbarStore } from '../stores/snackbar'

const regularAuthStore = useRegularAuthStore()
const { loading, error } = storeToRefs(regularAuthStore)
const { changePasswordWithToken } = regularAuthStore
const router = useRouter()

async function sendPasswordToken(params: { nuevaPassword: string; codigo: string }) {
  const { data, error } = await changePasswordWithToken(params)
  if (!error) {
    useSnackbarStore().showSnackbar({
      title: ':D',
      message: 'Puede iniciar sesión con su nueva contraseña',
      type: SnackbarType.SUCCESS
    })
    router.push('/login')
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
