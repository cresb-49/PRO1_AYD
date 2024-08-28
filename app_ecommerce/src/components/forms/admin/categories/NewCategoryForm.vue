<template>
  <v-card class="login-card pa-8 mt-4">
    <v-card-text>
      <v-form>
        <v-text-field
          v-model="nombreCategoria"
          label="Nombre Categoria"
          type="text"
          required
          :disabled="loading"
          prepend-icon="mdi-rename-outline"
        >
        </v-text-field>
        <v-text-field
          v-model="categoriaPadre"
          label="Categoria Padre"
          type="text"
          required
          :disabled="loading"
          prepend-icon="mdi-tournament"
        >
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
            @click="crear"
          >
            Crear
          </v-btn>
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
    }
  },
  emits: ['create'],
  data() {
    return {
      nombreCategoria: '',
      categoriaPadre: '',
    }
  },
  methods: {
    crear() {
      const newAttributes = {
        nombre: this.nombreCategoria.replace(/\s/g, '').trim(),
        categoriaPadre: this.categoriaPadre
      }
      this.$emit('create', newAttributes)
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
