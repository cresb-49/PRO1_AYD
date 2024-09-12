<template>
    <v-list v-if="products.length > 0">
        <v-list-item v-for="product in products" :key="product.id" class="cart-item">
            <v-row>
                <v-col cols="6">
                    <v-img :src="product.info ? (product.info.imagenesUrls![0]) : ''" alt="Product image"
                        class="cart-image"></v-img>
                </v-col>

                <v-col cols="6">
                    <v-list-item-content>
                        <v-list-item-title>{{ product.info?.nombre }}</v-list-item-title>
                        <v-list-item-subtitle>Cantidad: {{ product.quantity }}</v-list-item-subtitle>
                        <v-list-item-action class="mt-4"><v-btn icon="mdi-trash-can"
                                @click="eliminarProducto(product.id)"></v-btn></v-list-item-action>
                    </v-list-item-content>
                </v-col>
            </v-row>
            <v-divider class="mt-4 mb-2" />
        </v-list-item>
    </v-list>
    <h4 v-else>No hay productos en tu carrito!</h4>
</template>
<script setup lang="ts">
import { useCartStore, type CartInfo } from '@/stores/cart';

defineProps({
    products: {
        type: Array<CartInfo>,
        required: true
    }
})

const emits = defineEmits(['removeProduct'])

const { removeProduct } = useCartStore()

function eliminarProducto(product_id: number) {
    emits('removeProduct', product_id)
}
</script>