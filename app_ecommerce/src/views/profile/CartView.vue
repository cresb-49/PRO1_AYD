<template>
    <h1>Carrito</h1>
    {{ totalProducts }}
    {{ totalTax }}
    <v-row>
        <v-col cols="12" xs="6" sm="6">
            <h2>Productos</h2>
            <CartProductList :products="products" />
        </v-col>
        <v-col v-if="products.length > 0" cols="12" xs="6" sm="6">
            <h2>Resumen</h2>
            <CartSummaryForm :subtotal-prop="totalProducts" :tax-prop="totalTax" @buy="buy"></CartSummaryForm>
        </v-col>
    </v-row>
</template>
<script setup lang="ts">
import CartSummaryForm from '@/components/forms/shared/CartSummaryForm.vue';
import CartProductList from '@/components/partials/CartProductList.vue';
import router from '@/router';
import { useCartStore } from '@/stores/cart';
import { useRegularAuthStore } from '@/stores/regular-auth';
import { storeToRefs } from 'pinia';

const { fetchProductsCart, buyProducts } = useCartStore()
const { products, totalProducts, totalTax } = storeToRefs(useCartStore())

async function buy(params: {
    consumidorFinal: boolean,
    retiroEnTienda: boolean,
    pagoContraEntrega: boolean,
    direccion: string
}) {
    const attributesSale = {
        idComprador: useRegularAuthStore().user?.id!,
        consumidorFinal: params.consumidorFinal,
        retiroEnTienda: params.retiroEnTienda,
        pagoContraEntrega: params.pagoContraEntrega,
        direccion: params.direccion
    }
    const {data, error} = await buyProducts(attributesSale);
    if (error === false) {
        router.go(0)
    }
}

fetchProductsCart()
</script>