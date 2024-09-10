<template>
  <v-row>
    <v-col cols="12" xs="8" sm="12" md="6" lg="6" xl="6">
      <ImagesUploadForm ref="uploadImageForm"/>
    </v-col>
    <v-col cols="12" xs="3" sm="12" md="6" lg="6" xl="6">
      <v-card class="login-card pa-8 mt-4">
        <v-card-text>
          <v-form>
            <v-text-field
              v-model="nombreProducto"
              label="Nombre Producto"
              type="text"
              required
              :disabled="loading"
              prepend-icon="mdi-rename-outline"
            >
            </v-text-field>
            <v-select
              v-model="categoria"
              label="Categoria"
              :disabled="loading"
              prepend-icon="mdi-tournament"
              :items="categories"
              item-title="nombre"
              item-value="id"
            >
            </v-select>
            <v-text-field
            v-model="precio"
            label="Precio"
            density="compact"
            type="number"
            variant="outlined"
            hide-details
            prepend-icon="mdi-cash"
          ></v-text-field>
            <v-text-field
            v-model="impuesto"
            label="Impuesto"
            density="compact"
            type="number"
            variant="outlined"
            hide-details
            prepend-icon="mdi-cash"
            append-inner-icon="mdi-percent"
            class="mt-4"
          ></v-text-field>
            <v-text-field
            v-model="stock"
            label="Stock Inicial"
            density="compact"
            type="number"
            variant="outlined"
            hide-details
            prepend-icon="mdi-abacus"
            class="mt-4"
          ></v-text-field>
          </v-form>
        </v-card-text>
        <v-card-actions>
          <v-row>
            <v-col cols="12">
              <v-btn variant="tonal" width="100%" :loading="loading" @click="crear"> Crear </v-btn>
            </v-col>
          </v-row>
        </v-card-actions>
      </v-card>
    </v-col>
  </v-row>
</template>
<script setup lang="ts">
import { useCategoryStore } from '@/stores/categories'
import { storeToRefs } from 'pinia'
import { ref } from 'vue'
import ImagesUploadForm from '../../shared/ImagesUploadForm.vue';

const props = defineProps({
  loading: {
    type: Boolean,
    default: false
  }
})
const emits = defineEmits(['create'])

const nombreProducto = ref('')
const categoria = ref()
const precio = ref(0)
const impuesto = ref(0)
const stock = ref(0)
const uploadImageForm = ref(null)

const { categories } = storeToRefs(useCategoryStore())

function crear() {
  const newAttributes = {
    nombre: nombreProducto.value.trim(),
    categoria: categoria.value,
    precio: precio.value,
    impuesto: impuesto.value,
    stock: stock.value,
    imagenes: uploadImageForm.value!.images
  }
  emits('create', newAttributes)
}
</script>
