<template>
  <v-card class="login-card pa-8">
    <v-card-title>
      <h3 class="text-center mb-4">Restauracion de contraseña</h3>
    </v-card-title>
    <v-card-text>
      <v-form>
        <v-text-field
          v-model="password"
          :append-icon="showPassword ? 'mdi-eye-outline' : 'mdi-eye-off-outline'"
          label="Contraseña"
          :type="showPassword ? 'text' : 'password'"
          :rules="[validationRules.required, ...validationRules.password]"
          required
          @click:append="showPassword = !showPassword"
        >
          <template #prepend>
            <v-icon icon="mdi-dots-horizontal" size="small" />
          </template>
        </v-text-field>
        <v-text-field
          v-model="confirmPassword"
          :append-icon="showPassword ? 'mdi-eye-outline' : 'mdi-eye-off-outline'"
          label="Confirmar contraseña"
          :type="showPassword ? 'text' : 'password'"
          :rules="[validationRules.required, ...confirmPasswordRules]"
          required
          @click:append="showPassword = !showPassword"
        >
          <template #prepend>
            <v-icon icon="mdi-dots-horizontal" size="small" />
          </template>
        </v-text-field>
      </v-form>
    </v-card-text>
    <v-card-actions>
      <v-row>
        <v-col cols="12">
          <v-btn
            variant="tonal"
            width="100%"
            :loading="loading"
            color="accent-4"
            @click="sendPasswordToken"
          >
            Enviar correo
          </v-btn>
        </v-col>
      </v-row>
    </v-card-actions>
  </v-card>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { SnackbarType, useSnackbarStore } from '../../../stores/snackbar'

let showPassword = ref(false)
const password = ref('')
const confirmPassword = ref('')
const loading = ref(false)

//Obetenemos el valor c que viene en la query de la url
const c = new URLSearchParams(window.location.search).get('c')

const confirmPasswordRules = [(v: string) => v === password.value || 'Las contraseñas no coinciden']

const emits = defineEmits(['sendPasswordToken']) //Nombre del emit con "@""

const validationRules = {
  required: (v: string) => !!v || 'Campo requerido',
  name: [(v: string) => (v && v.length <= 100) || 'El nombre no debe exceder los 100 caracteres'],
  email: [(v: string) => /.+@([\w-]+\.)+.[\w-]{1,3}$/.test(v) || 'E-mail debe ser válido'],
  password: [
    (v: string) => (v && v.length >= 8) || 'La contraseña debe tener al menos 8 caracteres'
  ]
}

function sendPasswordToken() {
  const pass1 = password.value
  const pass2 = confirmPassword.value
  //Validacion de que las contraseñas sean iguales
  if (pass1 !== pass2) {
    useSnackbarStore().showSnackbar({
      title: 'Contraseñas no coinciden',
      message: 'Las contraseñas no coinciden, por favor verifique',
      type: SnackbarType.SUCCESS
    })
    return
  }
  //Funcion que emite un emit y envia el objeto emailData al view Padre
  const changePasswordData = {
    nuevaPassword: password.value,
    codigo: c
  }
  emits('sendPasswordToken', changePasswordData)
}
</script>

<style scoped lang="scss">
.nav-link {
  &:hover {
    color: #fb8c00 !important;
  }
  &:active {
    color: #bf360c !important;
  }
  &--admin {
    &:hover {
      color: #ff6e40 !important;
    }
    &:active {
      color: #bf360c !important;
    }
  }
}
</style>
