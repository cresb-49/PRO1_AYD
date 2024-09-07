<template>
    <v-list>
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
                    </v-list-item-content>
                </v-col>
            </v-row>
            <v-divider class="mt-4 mb-2" />
        </v-list-item>
    </v-list>
</template>
<script setup lang="ts">
import type { CartInfo } from '@/stores/cart';

    defineProps({
        products: {
            type: Array<CartInfo>,
            required: true
        }
    })
</script>