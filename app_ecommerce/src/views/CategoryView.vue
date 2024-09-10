<template>
    <v-container>
        <h2 class="mb-2">{{ categoria }}</h2>
        <v-btn prepend-icon="mdi-arrow-left" to="/" class="mb-3">
            Regresar
        </v-btn>
        <v-row>
            <v-col v-for="product in productsCategory" :key="product.id" cols="12" sm="6" md="4" lg="3">
                <v-card class="mx-auto" max-width="300">
                    <v-img :src="product === undefined
                        ? notFoundLink
                        : product.imagenesUrls === undefined ? notFoundLink : product.imagenesUrls[0]
                        " height="150px"></v-img>

                    <v-card-title>{{ product.nombre }}</v-card-title>
                    <v-card-subtitle>Q{{ product.precio }}</v-card-subtitle>
                    <v-card-actions>
                    <v-btn 
                        v-if="!isProductInCart(product.id)" 
                        @click="addToCart(product)"
                        prepend-icon="mdi-cart-outline"
                        >
                        Agregar
                    </v-btn>
                    <v-chip v-else color="green" text-color="white">En Carrito</v-chip>
                        <v-btn :to="`/producto/${product.id}`">Ver</v-btn>
                    </v-card-actions>
                </v-card>
            </v-col>
        </v-row>
    </v-container>
</template>

<script setup lang="ts">
import { useCartStore } from '@/stores/cart';
import { useCategoryStore, type Category } from '@/stores/categories';
import { useProductStore } from '@/stores/products';
import { storeToRefs } from 'pinia';
import { ref } from 'vue';
import { useRoute } from 'vue-router';

const notFoundLink = 'https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/No_image_available.svg/600px-No_image_available.svg.png'

const { fetchCategory } = useCategoryStore()
const { fetchProductsByCategory } = useProductStore()
const { productsCategory } = storeToRefs(useProductStore())
const {isProductInCart, addProduct} = useCartStore()

const categoria = ref('')

fetchCategory(useRoute().params.id as unknown as number).then(r => r.data.value.data as Category).then(category => {
    categoria.value = category.nombre
})

fetchProductsByCategory(useRoute().params.id as unknown as number)

function addToCart(product: any) {
    addProduct({id: product.id, quantity: 1})
}
</script>
<style scoped>
/* Optional styling */
</style>