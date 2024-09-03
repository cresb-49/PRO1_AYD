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
        <v-select
          v-model="categoriaPadre"
          label="Categoria Padre"
          clearable
          :disabled="loading"
          prepend-icon="mdi-tournament"
          :items="categories"
          item-title="nombre"
          item-value="id"
        >
        </v-select>
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
<script setup lang="ts">
import { useCategoryStore } from '@/stores/categories';
import { storeToRefs } from 'pinia';
import { ref } from 'vue';

  const props = defineProps({
    loading: {
      type: Boolean,
      default: false
    }
  })
  const emits = defineEmits(['create'])
  
  const nombreCategoria = ref('')
  const categoriaPadre = ref()
  
  const {categories} = storeToRefs(useCategoryStore())
  
  function crear() {
    const newAttributes = {
      nombre: nombreCategoria.value.trim(),
      categoriaPadre: categoriaPadre.value
    }
    emits('create', newAttributes)
  }
</script>