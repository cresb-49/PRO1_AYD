<template>
  <main>
    <header>
      <h1 class="mb-4">Categorias</h1>
      <v-btn prepend-icon="mdi-arrow-left" to="/admin" class="mt-3"> Regresar </v-btn>
      <v-btn prepend-icon="mdi-pencil-outline" to="/admin/categorias/add" class="mt-3">
        Agregar Categoria
      </v-btn>
    </header>
    <section>
      <TableData :columns="columns" :data="categories" data_key="id" :actions="actionsTable" />
    </section>
  </main>
</template>
<script setup lang="ts">
import { useCategoryStore } from '@/stores/categories'
import TableData from '../../../components/partials/TableData.vue'
import { storeToRefs } from 'pinia'

const { fetchAllCategories } = useCategoryStore()
const { categories } = storeToRefs(useCategoryStore())

const consoleLog = (i: number) => {}

const columns = [
  { name: 'Id', propertyName: 'id' },
  { name: 'Nombre', propertyName: 'nombre' }
]
const actionsTable = [
  { name: 'Editar', path: '/admin/categorias/edit/:id' },
  // { name: 'Eliminar', onClick: consoleLog }
]

fetchAllCategories()
</script>
