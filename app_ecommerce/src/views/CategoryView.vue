<template>
    <v-container>
        <h2 class="mb-2">{{ categoria }}</h2>
        <v-row>
            <v-col v-for="product in productsCategory" :key="product.id" cols="12" sm="6" md="4" lg="3">
                <v-card class="mx-auto" max-width="300" :to='`producto/${product.id}`'>
                    <v-img
                        :src="product === undefined 
                        ? notFoundLink 
                        : product.imagenesUrls === undefined ? notFoundLink : product.imagenesUrls[0]
                        "
                        height="150px"></v-img>

                    <v-card-title>{{ product.nombre }}</v-card-title>
                    <v-card-subtitle>Q{{ product.precio }}</v-card-subtitle>

                    <v-card-actions>
                        <v-btn v-if="!isInCart(product)" color="primary" @click="addToCart(product)">Agregar al Carrito</v-btn>
                        <v-chip v-else color="green" text-color="white">En Carrito</v-chip>
                    </v-card-actions>
                </v-card>
            </v-col>
        </v-row>
    </v-container>
</template>

<script setup lang="ts">
import { useCategoryStore, type Category } from '@/stores/categories';
import { useProductStore } from '@/stores/products';
import { storeToRefs } from 'pinia';
import { ref } from 'vue';
import { useRoute } from 'vue-router';

const notFoundLink = 'https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/No_image_available.svg/600px-No_image_available.svg.png'

const products = [
    {
        name: 'Product 1',
        price: 29.99,
        image: 'https://via.placeholder.com/150',
        id: 1,
    },
    {
        name: 'Product 2',
        price: 49.99,
        image: 'https://via.placeholder.com/150',
        id: 2,
    },
    // Add more products as needed
];

const {fetchCategory} = useCategoryStore()
const {fetchProductsByCategory} = useProductStore()
const {productsCategory} = storeToRefs(useProductStore())

const categoria = ref('')

fetchCategory(useRoute().params.id as unknown as number).then(r => r.data.value.data as Category).then(category => {
    categoria.value = category.nombre
})
fetchProductsByCategory(useRoute().params.id as unknown as number)

const cart = ref<number[]>([]); // Array to store product IDs in the cart

function addToCart(product: any) {
    if (!isInCart(product)) {
        cart.value.push(product.id);
        console.log('Added to cart:', product);
    }
}

function isInCart(product: any) {
    return cart.value.includes(product.id);
}
</script>
<style scoped>
/* Optional styling */
</style>