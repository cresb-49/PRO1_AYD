<template>
  <main>
    <header>
      <h1 class="mb-4">Productos</h1>
      <v-btn
        prepend-icon="mdi-arrow-left"
        to="/admin"
        class="mt-3"
      >
        Regresar
      </v-btn>
      <v-btn
        prepend-icon="mdi-pencil-outline"
        to="/admin/productos/add"
        class="mt-3"
      >
        Agregar Producto
      </v-btn>
    </header>
    <section>
      <TableData :columns="columns" :data="products" data_key="id" :actions="actionsTable"/>
    </section>
  </main>
</template>
<script setup lang="ts">
import TableData from "../../../components/partials/TableData.vue";
import { storeToRefs } from "pinia";
import { useProductStore } from "@/stores/products";

  const {fetchAllProducts} = useProductStore();
  const {products} = storeToRefs(useProductStore());
  
  const consoleLog = (i: number) => {
  }
  
  const columns = [
    {name: 'Id', propertyName: 'id'},
    {name: 'Nombre', propertyName: 'nombre'},
    {name: 'Precio', propertyName: 'precio'},
    {name: 'Stock', propertyName: 'stock'},
    {name: 'Categoria', propertyName: 'categoria.nombre'},
  ]
  const actionsTable = [{name: 'Editar', path: '/admin/productos/edit/:nombre'}, {name: 'Eliminar', onClick: consoleLog}]

  fetchAllProducts();
</script>