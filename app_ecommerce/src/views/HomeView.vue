<template>
  <main>
    <SimpleCardSlide :cards="mappedProducts" titulo="Ultimos Productos" class="mb-4"/>
    <SimpleCardSlide :cards="mappedProducts" titulo="Categorias"/>
  </main>
</template>
<script setup lang="ts">
import SimpleCardSlide from '@/components/partials/SimpleCardSlide.vue';
import { useProductStore, type Product } from '@/stores/products';
import { ref } from 'vue';

const {fetchAllProducts} = useProductStore()
const mappedProducts = ref({})

fetchAllProducts().then(r => r.data.value.data as Product[]).then(p => {
   mappedProducts.value = p.map(product => ({imageSrc: product.imagenesUrls![0], text: product.nombre})) 
})
</script>

