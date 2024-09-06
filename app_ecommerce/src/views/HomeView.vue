<template>
  <main>
    <v-row>
      <v-col cols="2" sm="1" md="1" lg="1"></v-col>
      <v-col cols="8" sm="10" md="10" lg="10">
        <v-form>
          <v-text-field
          v-model="busqueda"
          label="Que producto buscas?"
          density="compact"
          variant="outlined"
          hide-details
          prepend-inner-icon="mdi-magnify"
          class="mb-4"
        ></v-text-field>
        </v-form>
        <SimpleCardSlide :cards="mappedProducts" titulo="Ultimos Productos" class="mb-4" />
        <SimpleCardSlide :cards="mappedProducts" titulo="Categorias" />
      </v-col>
      <v-col cols="2" sm="1" md="1" lg="1"></v-col>
    </v-row>
  </main>
</template>
<script setup lang="ts">
import SimpleCardSlide from '@/components/partials/SimpleCardSlide.vue';
import { useProductStore, type Product } from '@/stores/products';
import { ref } from 'vue';

const { fetchAllProducts } = useProductStore()
const mappedProducts = ref({})
const busqueda = ref('')

fetchAllProducts().then(r => r.data.value.data as Product[]).then(p => {
  mappedProducts.value = p.map(product => ({ 
    imageSrc: product.imagenesUrls![0], 
    text: product.nombre, 
    path: `/producto/${product.id}`
  }))
})
</script>
