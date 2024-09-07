<template>
  <main>
    <header>
      <h1 class="mb-4">Inventario</h1>
      <v-btn
        prepend-icon="mdi-arrow-left"
        to="/admin"
        class="mt-3"
      >
        Regresar
      </v-btn>
    </header>
    <section class="mt-4">
      <h2>Productos con Poco Stock</h2>
      <TableData :columns="columns" :data="productsLowStock" data_key="id" :actions="actionsTable"/>
    </section>
    <section>
      <h2>Todos los Productos</h2>
      <TableData :columns="columns" :data="products" data_key="id" :actions="actionsTable"/>
    </section>
  </main>
</template>
<script setup lang="ts">
import TableData from "../../../components/partials/TableData.vue";
import { storeToRefs } from "pinia";
import { useProductStore } from "@/stores/products";

  const {fetchAllProducts, fetchWithLowStock, addUnitProducts} = useProductStore();
  const {products, productsLowStock} = storeToRefs(useProductStore());
  
  const agregarUnidad = (product_id: number) => {
    addUnitProducts(product_id);
  }
  
  const columns = [
    {name: 'Id', propertyName: 'id'},
    {name: 'Nombre', propertyName: 'nombre'},
    {name: 'Stock', propertyName: 'stock'},
    {name: 'Categoria', propertyName: 'categoria.nombre'},
  ]
  const actionsTable = [{name: 'Agregar Unidad', onClick: agregarUnidad}]

  fetchWithLowStock();
  fetchAllProducts();
</script>