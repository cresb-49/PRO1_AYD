<template>
  <main>
    <header class="mb-4">
      <h1 class="mb-4">Categorias - Editar</h1>
      <v-btn prepend-icon="mdi-arrow-left" to="/admin/categorias" class="mt-3">
        Regresar
      </v-btn>
    </header>
    <section class="edit-section">
      <EditCategoryForm :category_id="(route.params.id as unknown as number)" @update="updateCategoria"/>
    </section>
  </main>
</template>

<script setup lang="ts">
import EditCategoryForm from '@/components/forms/admin/categories/EditCategoryForm.vue';
import { useCategoryStore } from '@/stores/categories';
import { useRoute } from 'vue-router';

const route = useRoute()
const {fetchAllCategories, updateCategory} = useCategoryStore();

fetchAllCategories()

async function updateCategoria(params: {id: number, nombre: string, padre?: number}) {
  const newAttributes = {
    id: params.id,
    nombre: params.nombre,
    padre: params.padre
  }
  await updateCategory(newAttributes)
}
</script>

<style lang="scss" scoped>
.edit-section {
  margin-bottom: 1.5rem;
}
</style>
