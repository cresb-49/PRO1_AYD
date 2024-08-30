<template>
  <main>
    <header class="mb-4">
      <h1 class="mb-4">Categorias - Agregar</h1>
      <v-btn prepend-icon="mdi-arrow-left" to="/admin/categorias" class="mt-3">
        Regresar
      </v-btn>
    </header>
    <section class="">
      <NewCategoryForm @create="crearCategoria"/>
    </section>
  </main>
</template>

<script setup lang="ts">
import { useCategoryStore } from '@/stores/categories';
import NewCategoryForm from '@/components/forms/admin/categories/NewCategoryForm.vue';
import { useRouter } from 'vue-router';

const categoryStore = useCategoryStore();
const {createCategory, fetchAllCategories} = categoryStore;

const router = useRouter();

async function crearCategoria(parameters: {nombre: string, categoriaPadre: number}) {
  const {error} = await createCategory(parameters)
  if (error === false) {
    router.push('/admin/categorias');
  }
}

fetchAllCategories()
</script>

<style lang="scss" scoped>
.profile-edit-section {
  margin-bottom: 1.5rem;
}
</style>
