<template>
  <section>
    <div>
      <h2 class="font-weight-light">Perfil</h2>
      <h3 class="mb-4 text-overline">Información personal</h3>
      <v-row>
        <v-col cols="6">
          <v-text-field v-model="user.nombres" readonly variant="solo" label="Nombre(s)">
            <template #prepend>
              <v-icon icon="mdi-account-outline" size="small" />
            </template>
          </v-text-field>
        </v-col>
        <v-col cols="6">
          <v-text-field v-model="user.apellidos" readonly variant="solo" label="Apellido(s)">
            <template #prepend>
              <v-icon icon="mdi-account-outline" size="small" />
            </template>
          </v-text-field>
        </v-col>
        <v-col cols="12" sm="6" md="6" lg="6" xl="6">
          <v-text-field v-model="user.email" readonly variant="solo" label="Correo electrónico">
            <template #prepend>
              <v-icon icon="mdi-email-outline" size="small" />
            </template>
          </v-text-field>
        </v-col>
        <v-col cols="12" sm="6" md="6" lg="6" xl="6">
          <v-text-field
            v-if="role === 'regular'"
            v-model="user.nit"
            readonly
            variant="solo"
            label="NIT"
          >
            <template #prepend>
              <v-icon icon="mdi-file-document-outline" size="small" />
            </template>
          </v-text-field>
        </v-col>
        <v-col cols="12" sm="6" md="6" lg="6" xl="6">
          <v-btn style="margin: 2px" color="primary" @click="activarDosPasos(user.id)">
            Activar dos pasos
          </v-btn>
          <v-btn style="margin: 2px" color="primary" @click="desactivarDosPasos(user.id)">
            Desactivar dos pasos
          </v-btn>
        </v-col>
      </v-row>
    </div>
  </section>
</template>
<script lang="ts">
import type { PropType } from 'vue'
import { type User } from '../../../stores/regular-auth'
import { storeToRefs } from 'pinia'
import { useAuthStore } from '@/stores/auth'
import { useRegularAuthStore } from '@/stores/regular-auth'

const { role } = storeToRefs(useAuthStore())

export default {
  props: {
    src: {
      type: Object as PropType<User | null>,
      required: true
    }
  },
  emits: ['save'],
  data() {
    return {
      user: {
        nombres: '',
        apellidos: '',
        email: '',
        nit: ''
      },
      role: ''
    }
  },
  created() {
    const authStore = useAuthStore()
    const { role } = storeToRefs(authStore) // Extract only the 'role' from the store
    this.role = role.value as string // Assign role to data property
  },
  watch: {
    src: {
      immediate: true,
      handler(val) {
        this.user = { ...val }
      }
    }
  },
  methods: {
    async activarDosPasos(id: number) {
      const regularAuthStore = useRegularAuthStore()
      const { loading, error } = storeToRefs(regularAuthStore)
      const { setEstadoDosPasos } = regularAuthStore
      console.log('Activando dos pasos', id)

      await setEstadoDosPasos(true, id)
    },
    async desactivarDosPasos(id: number) {
      const regularAuthStore = useRegularAuthStore()
      const { loading, error } = storeToRefs(regularAuthStore)
      const { setEstadoDosPasos } = regularAuthStore
      console.log('Desactivando dos pasos', id)
      await setEstadoDosPasos(false, id)
    }
  }
}
</script>
