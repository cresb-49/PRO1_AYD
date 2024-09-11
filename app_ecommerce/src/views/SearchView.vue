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
          @click="buscar"
        ></v-text-field>
        </v-form>
          <v-btn
            type="button"
            prepend-icon="mdi-arrow-left"
            to="/"
            class="mb-2"
          >
            Regresar
          </v-btn>
        <h2>Resultados</h2>
        <v-row>
            <v-col v-for="product in foundProducts" :key="product.id" cols="12" sm="6" md="4" lg="3">
                <ProductPreviewCard :product="product"/>
            </v-col>
        </v-row>
      </v-col>
      <v-col cols="2" sm="1" md="1" lg="1"></v-col>
    </v-row>
  </main>
</template>
<script setup lang="ts">
import ProductPreviewCard from '@/components/partials/ProductPreviewCard.vue';
import { useProductStore } from '@/stores/products';
import { storeToRefs } from 'pinia';
import { ref } from 'vue';
import { useRoute } from 'vue-router';

const { fetchProductsSearch } = useProductStore()
const { foundProducts } = storeToRefs(useProductStore())
const busqueda = ref(useRoute().params.busqueda as string)

function buscar() {
    fetchProductsSearch(useRoute().params.busqueda as string)
}
fetchProductsSearch(useRoute().params.busqueda as string)
</script>
