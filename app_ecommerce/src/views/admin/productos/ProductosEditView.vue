<template>
  <main>
    <header class="mb-4">
      <h1 class="mb-4">Productos - Editar</h1>
      <v-btn prepend-icon="mdi-arrow-left" to="/admin/productos" class="mt-3">
        Regresar
      </v-btn>
    </header>
    <section class="edit-section">
      <EditProductForm :product_id="(route.params.id as unknown as number)" @update="updateProducto"/>
    </section>
  </main>
</template>

<script setup lang="ts">
import EditProductForm from '@/components/forms/admin/products/EditProductForm.vue';
import { useCategoryStore } from '@/stores/categories';
import { useProductStore } from '@/stores/products';
import { useRoute } from 'vue-router';

const route = useRoute()
const {fetchAllCategories} = useCategoryStore();
const {updateProduct} = useProductStore();

fetchAllCategories()

async function updateProducto(params: {id: number, nombre: string, categoria: number, precio: number, stock: number, impuesto: number}) {
  const newAttributes = {
    id: params.id,
    nombre: params.nombre,
    categoria: params.categoria,
    precio: params.precio,
    stock: params.stock,
    porcentajeImpuesto: params.impuesto,
    habilitado: true
  }
  await updateProduct(newAttributes)
}
</script>

<style lang="scss" scoped>
.edit-section {
  margin-bottom: 1.5rem;
}
</style>
