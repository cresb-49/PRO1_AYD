<template>
    <v-container>
        <h2 class="mb-2">{{ categoria }}</h2>
        <v-btn prepend-icon="mdi-arrow-left" to="/" class="mb-3">
            Regresar
        </v-btn>
        <v-row>
            <v-col v-for="product in productsCategory" :key="product.id" cols="12" sm="6" md="4" lg="3">
                <ProductPreviewCard :product="product"/>
            </v-col>
        </v-row>
    </v-container>
</template>

<script setup lang="ts">
import ProductPreviewCard from '@/components/partials/ProductPreviewCard.vue';
import { useCategoryStore, type Category } from '@/stores/categories';
import { useProductStore } from '@/stores/products';
import { storeToRefs } from 'pinia';
import { ref } from 'vue';
import { useRoute } from 'vue-router';

const { fetchCategory } = useCategoryStore()
const { fetchProductsByCategory } = useProductStore()
const { productsCategory } = storeToRefs(useProductStore())

const categoria = ref('')

fetchCategory(useRoute().params.id as unknown as number).then(r => r.data.value.data as Category).then(category => {
    categoria.value = category.nombre
})

fetchProductsByCategory(useRoute().params.id as unknown as number)
</script>
<style scoped>
/* Optional styling */
</style>