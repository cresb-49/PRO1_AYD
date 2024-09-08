<template>
  <v-card class="login-card pa-8">
    <v-card-title>
      <h3 class="text-center mb-4">Token de autenticación</h3>
    </v-card-title>
    <v-card-text>
      <v-form>
        <v-text-field
          v-model="email_value"
          label="Correo electrónico"
          type="email"
          required
          :disabled="true"
        >
          <template #prepend>
            <v-icon :icon="emailIcon" size="small" />
          </template>
        </v-text-field>
        <v-text-field
          v-model="twoFactorCode"
          label="Token de autenticación"
          required
          :disabled="loading"
        >
          <template #prepend>
            <v-icon :icon="keyIcon" size="small" />
          </template>
        </v-text-field>
      </v-form>
    </v-card-text>
    <v-card-actions>
      <v-row>
        <!-- <v-col v-if="error" cols="12">
            <span class="text-red d-flex align-center">
              <v-icon class="mr-3"> mdi-alert-circle-outline </v-icon>
              <span class="font-weight-medium text-body-2">{{ error }}</span>
            </span>
          </v-col> -->
        <v-col cols="12">
          <v-btn
            variant="tonal"
            width="100%"
            :loading="loading"
            :color="admin ? 'red' : 'accent-4'"
            @click="login"
          >
            Iniciar sesión
          </v-btn>
        </v-col>
        <v-col cols="12">
          <span class="d-flex align-center">
            <v-btn to="/sign-up" class="ml-1" variant="plain" :ripple="false">
              ¿Olvidaste tu contraseña?
            </v-btn>
          </span>
        </v-col>
        <v-divider></v-divider>
        <v-col v-if="showSignup" cols="12">
          <span class="d-flex align-center">
            ¿No tienes una cuenta?
            <v-btn to="/sign-up" class="ml-1" variant="plain" :ripple="false">
              <strong>Regístrate</strong>
            </v-btn>
          </span>
        </v-col>
        <v-col cols="12">
          <v-row>
            <span>
              <v-btn
                to="/login"
                class="px-0"
                variant="plain"
                :ripple="false"
                prepend-icon="mdi-arrow-left"
              >
                <strong>Regresar a Login</strong>
              </v-btn>
            </span>
          </v-row>
          <v-row>
            <span>
              <v-btn
                to="/"
                class="px-0"
                variant="plain"
                :ripple="false"
                prepend-icon="mdi-arrow-left"
              >
                <strong>Regresar al Homepage</strong>
              </v-btn>
            </span>
          </v-row>
        </v-col>
      </v-row>
    </v-card-actions>
  </v-card>
</template>
<script lang="ts">
export default {
  props: {
    loading: {
      type: Boolean,
      default: false
    },
    admin: {
      type: Boolean,
      default: false
    },
    showSignup: {
      type: Boolean,
      default: true
    },
    email: {
      type: String,
      default: ''
    }
  },
  emits: ['login'],
  data() {
    return {
      twoFactorCode: '',
      email_value: this.email
    }
  },
  computed: {
    linkColor() {
      return this.admin ? 'text-deep-orange-accent-4 nav-link--admin' : 'text-orange-darken-3'
    },
    emailIcon() {
      return this.admin ? 'mdi-shield-crown-outline' : 'mdi-email-outline'
    },
    keyIcon() {
      return this.admin ? 'mdi-cellphone-key' : 'mdi-cellphone-key'
    }
  },
  methods: {
    login() {
      const newCredentials = {
        email: this.email,
        twoFactorCode: this.twoFactorCode.replace(/\s/g, '').trim()
      }
      this.$emit('login', newCredentials)
    }
  }
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
