<template>
  <section>
    <div>
      <h2 class="font-weight-light">Perfil</h2>
      <h3 class="mb-4 text-overline">Información personal</h3>
      <v-row>
        <v-col cols="6">
          <v-text-field
            v-model="user.nombres"
            readonly
            variant="solo"
            label="Nombre(s)"
          >
            <template #prepend>
              <v-icon icon="mdi-account-outline" size="small" />
            </template>
          </v-text-field>
        </v-col>
        <v-col cols="6">
          <v-text-field
            v-model="user.apellidos"
            readonly
            variant="solo"
            label="Apellido(s)"
          >
            <template #prepend>
              <v-icon icon="mdi-account-outline" size="small" />
            </template>
          </v-text-field>
        </v-col>
        <v-col cols="12" sm="6" md="6" lg="6" xl="6">
          <v-text-field
            v-model="user.email"
            readonly
            variant="solo"
            label="Correo electrónico"
          >
            <template #prepend>
              <v-icon icon="mdi-email-outline" size="small" />
            </template>
          </v-text-field>
        </v-col>
      </v-row>
    </div>
  </section>
</template>
<script lang="ts">
import type { PropType } from 'vue';
import { type User } from '../../../stores/regular-auth'

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
      }
    }
  },
  watch: {
    src: {
      immediate: true,
      handler(val) {
        this.user = { ...val }
      }
    }
  }
}
</script>
