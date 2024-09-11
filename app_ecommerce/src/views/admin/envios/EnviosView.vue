<template>
    <main>
      <header>
        <h1 class="mb-4">Envíos</h1>
        <v-btn
          prepend-icon="mdi-arrow-left"
          to="/admin"
          class="mt-3"
        >
          Regresar
        </v-btn>
      </header>      
      <section>
        <TableData :columns="columns" :data="orders" data_key="id" :actions="actionsTable"/>
      </section>
    </main>
</template>


<script setup lang="ts">
import { useOrderStore } from '@/stores/orders';
import TableData from '@/components/partials/TableData.vue';
import { storeToRefs } from 'pinia';

// Acceder al store de órdenes
// const { fetchAllOrders } = useOrderStore();
// const orders = storeToRefs(useOrderStore());
const orderStore = useOrderStore();
const { orders } = storeToRefs(orderStore); // Solo usas storeToRefs para propiedades reactivas
const { fetchAllOrders } = orderStore; // Llamas a las acciones directamente sin storeToRefs

// Definir las columnas de la tabla
const columns = [
  { name: 'ID Factura', propertyName: 'venta.datosFacturacion.nit' },
  { name: 'ID Venta', propertyName: 'venta.id' },
  { name: 'ID Pedido', propertyName: 'id' },
  { name: 'Dirección', propertyName: 'direccion' },
  { name: 'Estado Pedido', propertyName: 'estadoEnvio.nombre' }
];

// Acción al presionar "Completar"
const completeOrder = (orderId: number) => {
  orderStore.completeOrder(orderId);
};

// Definir las acciones de la tabla
const actionsTable = [
  {
    name: 'Completar',
    onClick: completeOrder
  }
];

// Llamar a fetchAllOrders al cargar la vista
fetchAllOrders();
</script>