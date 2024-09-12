<template>
  <section class="login-page">
    <v-row justify="center" no-gutters>
      <v-col cols="11" sm="8" md="8" lg="5" xl="4">
        <SignupForm
          :loading="loading"
          :error="error"
          @signup="signUp($event)"
          title="Registro Administrador"
          :submitType="UserRole.ADMIN"
          :adminView="true"
          :success="success"
        />
      </v-col>
    </v-row>
  </section>
</template>
<script setup lang="ts">
import { storeToRefs } from 'pinia'
import { useRegularAuthStore, UserRole, type SignupPayload } from '../../../stores/regular-auth'
import SignupForm from '../../../components/forms/accounts/SignupForm.vue'
import { useRouter } from 'vue-router'
import { SnackbarType, useSnackbarStore } from '@/stores/snackbar'
import { ref } from 'vue'

const regularAuthStore = useRegularAuthStore()
const { loading, error } = storeToRefs(regularAuthStore)
const { signupUser } = regularAuthStore
const router = useRouter()

const success = ref(false)

async function signUp(payload: SignupPayload) {
  console.log('payload', payload);
  
  const { error } = await signupUser(payload, UserRole.ADMIN, false)
  if (error === false) {
    success.value = true
    useSnackbarStore().showSnackbar({
      title: 'Administrador registrado',
      message: 'El administrador ha sido registrado exitosamente',
      type: SnackbarType.SUCCESS
    })
  } else {
    success.value = false
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
