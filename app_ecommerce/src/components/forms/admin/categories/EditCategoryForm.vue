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
            @click="update"
          >
            Guardar
          </v-btn>
        </v-col>
      </v-row>
    </v-card-actions>
  </v-card>
</template>
<script setup lang="ts">
import { useCategoryStore } from '@/stores/categories';
import { type Category } from '@/stores/categories';
import { storeToRefs } from 'pinia';
import { ref, type PropType } from 'vue';

  const props = defineProps({
    loading: {
      type: Boolean,
      default: false
    },
    category_id: {
      type: Number,
      required: true
    }
  })
  const emits = defineEmits(['update'])
  
  const nombreCategoria = ref('')
  const categoriaPadre = ref()
  
  const {categories} = storeToRefs(useCategoryStore())
  const {fetchCategory} = useCategoryStore()
  
  fetchCategory(props.category_id).then(r => r.data.value.data as Category).then(c => {
    nombreCategoria.value = c.nombre;
    categoriaPadre.value = c.padre;
  });
  
  function update() {
    const newAttributes = {
      id: props.category_id,
      nombre: nombreCategoria.value.trim(),
      categoriaPadre: categoriaPadre.value
    }
    emits('update', newAttributes)
  }
  
</script>