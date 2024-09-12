<template>
    <v-card class="mx-auto" max-width="300">
        <v-img :src="product === undefined
            ? notFoundLink
            : product.imagenesUrls === undefined ? notFoundLink : product.imagenesUrls[0]
            " height="150px"></v-img>

        <v-card-title>{{ product.nombre }}</v-card-title>
        <v-card-subtitle>Q{{ product.precio }}</v-card-subtitle>
        <v-card-actions>
            <v-div v-if="role === 'regular'">
                <v-btn v-if="!isProductInCart(product.id)" @click="addToCart(product)" prepend-icon="mdi-cart-outline">
                    Agregar
                </v-btn>
                <v-chip v-else color="green" text-color="white">En Carrito</v-chip>
            </v-div>
            <v-btn :to="`/producto/${product.id}`">Ver</v-btn>
        </v-card-actions>
    </v-card>
</template>
<script setup lang="ts">
import { useAuthStore } from '@/stores/auth';
import { useCartStore } from '@/stores/cart';
import type { Product } from '@/stores/products';
import { storeToRefs } from 'pinia';
import type { PropType } from 'vue';

const { addProduct, isProductInCart } = useCartStore()
const { role } = storeToRefs(useAuthStore())
const notFoundLink = 'https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/No_image_available.svg/600px-No_image_available.svg.png'

const props = defineProps({
    product: {
        type: Object as PropType<Product>,
        required: true
    },
})

function addToCart(product: any) {
    addProduct({ id: product.id, quantity: 1 })
}
</script>