<template>
  <main>
    <header class="mb-4">
      <h1 class="mb-4">Producto</h1>
      <v-btn prepend-icon="mdi-arrow-left" @click="$router.go(-1)" class="mt-3">
        Regresar
      </v-btn>
    </header>
    <section class="edit-section">
      <BuyProductForm :producto="producto" @addToCart="agregarAlCarrito" @removeFromCart="removerDelCarrito" ref="buyForm"/>
    </section>
  </main>
</template>

<script setup lang="ts">
import BuyProductForm from '@/components/forms/shared/BuyProductForm.vue';
import { useCartStore } from '@/stores/cart';
import { useCategoryStore } from '@/stores/categories';
import { useProductStore, type Product } from '@/stores/products';
import { onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';

const route = useRoute()
const {fetchAllCategories} = useCategoryStore();
const {fetchProduct} = useProductStore();
const {addProduct, removeProduct, isProductInCart} = useCartStore()

const producto = ref()
const buyForm = ref<InstanceType<typeof BuyProductForm> | null>(null)

fetchAllCategories()

onMounted(() => {
  fetchProduct(route.params.id as unknown as number).then(r => r.data.value.data as Product).then(p => {
    producto.value = p
    const result = isProductInCart(p.id)
    buyForm.value!.enCarrito = result
  });
})

function agregarAlCarrito(params: {cantidad: number}) {
  const productInfo = {
    id: route.params.id as unknown as number,
    quantity: params.cantidad
  }
  const result = addProduct(productInfo)
  if (result) {
    buyForm.value!.enCarrito = true
  }
}

function removerDelCarrito() {
  const result = removeProduct(route.params.id as unknown as number)
  if (result) {
    buyForm.value!.enCarrito = !result
  }
}
</script>

<style lang="scss" scoped>
.edit-section {
  margin-bottom: 1.5rem;
}
</style>
