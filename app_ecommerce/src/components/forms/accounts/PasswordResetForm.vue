<template>
  <v-card class="login-card pa-8">
    <v-card-title>
      <h3 class="text-center mb-4">Recuperar contraseña</h3>
    </v-card-title>
    <v-card-text>
      <v-form>
        <v-text-field
          v-model="email"
          label="Correo electrónico"
          type="email"
          required
          :disabled="loading"
        >
          <template #prepend>
            <v-icon icon="mdi-email-outline" size="small" />
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
            @click="sendEmail"
          >
            Enviar correo
          </v-btn>
        </v-col>
        <v-divider></v-divider>
        <v-col cols="12">
          <v-row>
            <span>
              <v-btn
                to="/"
                class="px-0"
                variant="plain"
                :ripple="false"
                prepend-icon="mdi-arrow-left"
              >
                <strong>Regresar a Homepage</strong>
              </v-btn>
            </span>
          </v-row>
          <v-row>
            <span>
              <v-btn
                to="/login"
                class="px-0"
                variant="plain"
                :ripple="false"
                prepend-icon="mdi-arrow-left"
              >
                <strong>Regresar a Iniciar sesión</strong>
              </v-btn>
            </span>
          </v-row>
        </v-col>
      </v-row>
    </v-card-actions>
  </v-card>
</template>

<script setup lang="ts">
import { ref } from 'vue'

const email = ref('')
const loading = ref(false)
const emits = defineEmits(['sendEmail']) //Nombre del emit con "@""

function sendEmail() {
  //Funcion que emite un emit y envia el objeto emailData al view Padre
  const emailData = {
    correoElectronico: email.value
  }
  emits('sendEmail', emailData)
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
