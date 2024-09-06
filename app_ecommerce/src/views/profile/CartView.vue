<template>
    <h1>Carrito</h1>
    <v-row>
        <v-col cols="12" xs="6" sm="6">
            <h2>Productos</h2>
            <CartProductList :products="products" />
        </v-col>
        <v-col cols="12" xs="6" sm="6">
            <h2>Resumen</h2>
            <CartSummaryForm :subtotal-prop="totalProducts"></CartSummaryForm>
        </v-col>
    </v-row>
</template>
<script setup lang="ts">
import CartSummaryForm from '@/components/forms/shared/CartSummaryForm.vue';
import CartProductList from '@/components/partials/CartProductList.vue';
import { useCartStore } from '@/stores/cart';

const { products, totalProducts, fetchProductsCart } = useCartStore()

fetchProductsCart()
</script>