<template>
  <main>
    <header>
      <h1 class="mb-4">Categorias</h1>
      <v-btn
        prepend-icon="mdi-arrow-left"
        to="/admin"
        class="mt-3"
      >
        Regresar
      </v-btn>
      <v-btn
        prepend-icon="mdi-pencil-outline"
        to="/admin/categorias/add"
        class="mt-3"
      >
        Agregar Categoria
      </v-btn>
    </header>
    <section>
      <TableData :columns="['Id', 'Nombres']" :data="dataTable" data_key="id" :actions="actionsTable"/>
    </section>
  </main>
</template>
<script setup lang="ts">
  import TableData from "../../../components/partials/TableData.vue";
  
  const dataTable = [
    {id: '1', name: 'Categoria1'},
    {id: '2', name: 'Categoria2'},
    {id: '3', name: 'Categoria3'},
  ]
  
  const consoleLog = (i: number) => {
  }
  
  const actionsTable = [{name: 'Editar', path: '/admin/categorias/edit/:id'}, {name: 'Disable', onClick: consoleLog}]
</script>