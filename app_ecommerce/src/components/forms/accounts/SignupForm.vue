<template>
  <v-card class="signup-card pa-8">
    <v-card-title>
      <h3 class="text-center mb-4">{{ title }}</h3>
    </v-card-title>
    <v-card-text>
      <v-form id="signup-form" ref="form" :disabled="loading" @submit.prevent="signup">
        <v-text-field
          v-model="nombres"
          label="Nombre(s)"
          type="text"
          :rules="[validationRules.required, ...validationRules.name]"
          pattern="[a-zA-Z]{100}"
          required
        >
          <template #prepend>
            <v-icon icon="mdi-account-outline" size="small" />
          </template>
        </v-text-field>
        <v-text-field
          v-model="apellidos"
          label="Apellido(s)"
          type="text"
          :rules="[validationRules.required, ...validationRules.name]"
          pattern="[a-zA-Z]{100}"
          required
        >
          <template #prepend>
            <v-icon icon="mdi-account-outline" size="small" />
          </template>
        </v-text-field>
        <v-text-field
          v-model="email"
          label="Correo electrónico"
          :rules="[validationRules.required, ...validationRules.email]"
          type="text"
          name="email"
          required
          autocomplete="email"
        >
          <template #prepend>
            <v-icon icon="mdi-email-open-outline" size="small" />
          </template>
        </v-text-field>
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
        <v-col v-if="error && !Array.isArray(error)" cols="12">
          <span class="text-red d-flex align-center">
            <v-icon class="mr-3"> mdi-alert-circle-outline </v-icon>
            <span class="font-weight-medium text-body-2">{{ error }}</span>
          </span>
        </v-col>
        <v-col v-if="Array.isArray(error)" cols="12">
          <span class="text-red">
            <v-icon class="mr-3 mb-2"> mdi-alert-circle-outline </v-icon>
            <div v-for="e in error" :key="e as string" class="font-weight-medium text-body-2">
              {{ e }}
            </div>
          </span>
        </v-col>
        <v-col cols="12">
          <v-btn type="submit" form="signup-form" variant="tonal" width="100%" :loading="loading">
            {{ adminView ? 'Registrar' : 'Continuar' }}
          </v-btn>
        </v-col>
        <v-col cols="12" v-if="adminView">
          <v-btn
            type="button"
            variant="tonal"
            width="100%"
            prepend-icon="mdi-arrow-left"
            @click="$router.go(-1)"
          >
            Regresar
          </v-btn>
        </v-col>
        <v-col cols="12" v-if="!adminView">
          <span>
            ¿Ya tienes una cuenta?
            <router-link to="/login" class="text-orange-darken-3 text-decoration-none nav-link ml-1"
              ><strong>Inicia sesión</strong></router-link
            >
          </span>
        </v-col>
      </v-row>
    </v-card-actions>
  </v-card>
</template>
<script lang="ts">
import { UserRole } from '@/stores/regular-auth'

export default {
  props: {
    loading: {
      type: Boolean,
      default: false
    },
    error: {
      type: [String, Array],
      default: () => null
    },
    success: {
      type: Boolean,
      default: false
    },
    title: {
      type: String,
      default: 'Regístrate'
    },
    submitType: {
      type: String,
      default: UserRole.USUARIO
    },
    adminView: {
      type: Boolean,
      default: false
    }
  },
  watch: {
    success(newValue) {
      if (newValue) {
        this.clearForm()
      }
    }
  },
  emits: ['signup'],
  data() {
    return {
      nombres: '',
      apellidos: '',
      email: '',
      password: '',
      confirmPassword: '',
      showPassword: false,
      validationRules: {
        required: (v: string) => !!v || 'Campo requerido',
        name: [
          (v: string) => (v && v.length <= 100) || 'El nombre no debe exceder los 100 caracteres'
        ],
        email: [(v: string) => /.+@([\w-]+\.)+.[\w-]{1,3}$/.test(v) || 'E-mail debe ser válido'],
        password: [
          (v: string) => (v && v.length >= 8) || 'La contraseña debe tener al menos 8 caracteres'
        ]
      }
    }
  },
  computed: {
    confirmPasswordRules() {
      return [(v: string) => v === this.password || 'Las contraseñas no coinciden']
    }
  },
  methods: {
    async signup() {
      const formRef = await this.$refs.form.validate()
      if (formRef.valid) {
        //Si es de tipo AYUDANTE, el payload cambia
        if (this.submitType === UserRole.AYUDANTE) {
          const payload = {
            usuario: {
              nombres: this.nombres,
              apellidos: this.apellidos,
              email: this.email,
              password: this.password
            },
            permisos: []
          }
          this.$emit('signup', payload)
        } else {
          const payload = {
            nombres: this.nombres,
            apellidos: this.apellidos,
            email: this.email,
            password: this.password
          }
          this.$emit('signup', payload)
        }
      }
    },
    async clearForm() {
      //Agregamos una pausa de 1 segundo y limpiamos los campos del formulario
      await new Promise((resolve) => setTimeout(resolve, 1000))
      this.nombres = ''
      this.apellidos = ''
      this.email = ''
      this.password = ''
      this.confirmPassword = ''
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
}
</style>
