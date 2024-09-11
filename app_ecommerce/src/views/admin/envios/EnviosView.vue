<template>
  <main>
    <header>
      <h1 class="mb-4">Envíos</h1>
      <v-btn prepend-icon="mdi-arrow-left" to="/admin" class="mt-3">
        Regresar
      </v-btn>
    </header>
    
    <section>
      <!-- Mostrar mensaje si no hay envíos -->
      <p v-if="orders.length === 0">No hay envíos disponibles</p>

      <!-- Mostrar la tabla siempre, con o sin datos -->
       <!-- Enviar el array orders si hay datos, si no enviar un array vacío -->
      <TableData
        v-else
        :columns="columns"
        :data="orders.length > 0 ? orders : []"
        data_key="id"
        :actions="actionsTable"
      />
    </section>
  </main>
</template>

<script setup lang="ts">
import { useOrderStore } from '@/stores/orders';
import TableData from '@/components/partials/TableData.vue';
import { storeToRefs } from 'pinia';

// Acceder al store de órdenes
const orderStore = useOrderStore();
const { orders } = storeToRefs(orderStore); // Para propiedades reactivas
const { fetchAllOrders } = orderStore; // Para las acciones

// Definir las columnas de la tabla
const columns = [
  { name: 'ID Factura', propertyName: 'venta.datosFacturacion.id' },
  { name: 'ID Venta', propertyName: 'venta.id' },
  { name: 'ID Pedido', propertyName: 'id' },
  { name: 'Dirección', propertyName: 'direccion' },
  { name: 'Estado Pedido', propertyName: 'estadoEnvio.nombre' }
];

// Acción al presionar "Ver"
const viewOrder = (orderId: number) => {
  orderStore.viewOrder(orderId);
};

// Definir las acciones de la tabla
const actionsTable = [
  {
    name: 'Ver',
    onClick: viewOrder
  }
];

// Llamar a fetchAllOrders al cargar la vista
fetchAllOrders().then(() => {
  console.log('Orders in EnviosView:', orders.value); // Mostrar las órdenes que se pasarán a TableData
});
</script>
