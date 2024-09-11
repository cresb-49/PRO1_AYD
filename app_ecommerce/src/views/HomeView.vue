<template>
  <main>
    <v-row>
      <v-col cols="2" sm="1" md="1" lg="1"></v-col>
      <v-col cols="8" sm="10" md="10" lg="10">
        <v-form @submit.prevent>
          <v-text-field
          v-model="busqueda"
          label="Que producto buscas?"
          density="compact"
          variant="outlined"
          hide-details
          prepend-inner-icon="mdi-magnify"
          class="mb-4"
          @keydown.enter="buscar"
        ></v-text-field>
        </v-form>
        <SimpleCardSlide :cards="mappedProducts" titulo="Ultimos Productos" class="mb-4" />
        <h2>Categorias</h2>
        <CardGrid :cards="items"/>
      </v-col>
      <v-col cols="2" sm="1" md="1" lg="1"></v-col>
    </v-row>
  </main>
</template>
<script setup lang="ts">
import CardGrid from '@/components/partials/CardGrid.vue';
import SimpleCardSlide from '@/components/partials/SimpleCardSlide.vue';
import router from '@/router';
import { useCategoryStore } from '@/stores/categories';
import { useProductStore, type Product } from '@/stores/products';
import { storeToRefs } from 'pinia';
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const { fetchLastProducts, fetchProductsSearch } = useProductStore()
const { fetchAllCategories, categories } = useCategoryStore()
const {categoriesHomepage} = storeToRefs(useCategoryStore())
const mappedProducts = ref<any[]>([])
const busqueda = ref('')

const items = ref<any[]>([])

function buscar() {
    router.push(`/buscar/${busqueda.value}`)
}

fetchLastProducts().then(r => r.data.value.data as Product[]).then(p => {
  mappedProducts.value = p.map(product => ({
    imageSrc: product.imagenesUrls![0],
    text: product.nombre,
    path: `/producto/${product.id}`
  }))
})

fetchAllCategories().then(r => r.data.value.data).then(c => {
  categoriesHomepage.value.then(list => {
    console.log('lista')
    console.log(list)
    items.value = list
  })
})
</script>
