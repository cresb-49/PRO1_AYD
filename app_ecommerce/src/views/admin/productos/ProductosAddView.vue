<template>
  <main>
    <header class="mb-4">
      <h1 class="mb-4">Productos - Agregar</h1>
      <v-btn prepend-icon="mdi-arrow-left" to="/admin/productos" class="mt-3">
        Regresar
      </v-btn>
    </header>
    <section class="">
      <NewProductForm @create="crearProducto"/>
    </section>
  </main>
</template>

<script setup lang="ts">
import { useCategoryStore } from '@/stores/categories';
import { useRouter } from 'vue-router';
import NewProductForm from '@/components/forms/admin/products/NewProductForm.vue';
import { useProductStore } from '@/stores/products';

const categoryStore = useCategoryStore();
const {fetchAllCategories} = categoryStore;
const productStore = useProductStore();
const {createProduct} = productStore;

const router = useRouter();

async function crearProducto(parameters: {categoria: number, nombre: string, stock: number, impuesto: number, precio: number, imagenes: Array<File>}) {
console.log('aqui esta esto')
  const {error} = await createProduct(parameters)
  if (error === false) {
    router.push('/admin/productos');
  }
}

fetchAllCategories()
</script>

<style lang="scss" scoped>
.profile-edit-section {
  margin-bottom: 1.5rem;
}
</style>
