<template>
    <h1>Carrito</h1>
    <h2>Productos</h2>
  <v-card
    class="mx-auto"
    max-width="300"
  >
    <v-list density="compact">
      <v-list-item
        v-for="product in products"
        :key="product.id"
        :value="product.id"
        color="primary"
      >
        <template v-slot:append>
            Cantidad: {{ product.quantity }}
        </template>

        <v-list-item-title v-text="`ID: ${product.id}`"></v-list-item-title>
      </v-list-item>
    </v-list>
  </v-card>
</template>
<script setup lang="ts">
import { useCartStore } from '@/stores/cart';

    const {products} = useCartStore()
</script>