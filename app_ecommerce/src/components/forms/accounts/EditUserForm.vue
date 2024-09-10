<template>
  <v-card class="signup-card pa-8">
    <v-card-title>
      <h3 class="text-center mb-4">{{ title }}</h3>
    </v-card-title>
    <v-card-text>
      <v-form id="signup-form" ref="form" :disabled="loading" @submit.prevent="editUser">
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
          required
        >
          <template #prepend>
            <v-icon icon="mdi-email-open-outline" size="small" />
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
            Editar
          </v-btn>
        </v-col>
        <v-col cols="12">
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
      </v-row>
    </v-card-actions>
  </v-card>
</template>
<script lang="ts">
import type { UpdateUserPayload, UserByPermiso } from '@/stores/users'

export default {
  props: {
    loading: {
      type: Boolean,
      default: false
    },
    error: {
      type: [String, Array, Boolean],
      default: () => null
    },
    title: {
      type: String,
      default: 'Editar Usuario'
    },
    user: {
      type: Object as () => UserByPermiso,
      default: () => ({}) as UserByPermiso
    }
  },
  watch: {
    user: {
      handler: function (val) {
        this.nombres = val.nombres
        this.apellidos = val.apellidos
        this.email = val.email
      },
      deep: true
    }
  },
  emits: ['editUser'],
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
    async editUser() {
      const formRef = await this.$refs.form.validate()
      if (formRef.valid) {
        const payload = {
          id: this.user.id,
          nombres: this.nombres,
          apellidos: this.apellidos,
          email: this.email,
          nit: this.user.nit
        } as UpdateUserPayload
        this.$emit('editUser', payload)
      }
    }
  },
  mounted() {
    this.nombres = this.user.nombres
    this.apellidos = this.user.apellidos
    this.email = this.user.email
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
