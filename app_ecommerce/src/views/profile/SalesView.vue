<template>
  <main>
    <header>
      <h1 class="mb-4">Mis Compras</h1>
      <v-btn prepend-icon="mdi-arrow-left" to="/perfil" class="mt-3">
        Regresar
      </v-btn>
    </header>
    <section style="margin-top: 5px;">
      <TableData :columns="columns" :data="sales" data_key="id" :actions="actionsTable" />
    </section>
  </main>
</template>

<script setup lang="ts">
import TableData from '../../components/partials/TableData.vue'
import { storeToRefs } from 'pinia'
import { useSalesStore } from '@/stores/sales'

const { fetchAllSalesOfUser } = useSalesStore()
const { sales } = storeToRefs(useSalesStore())

const columns = [
  { name: 'Id Venta', propertyName: 'id' },
  { name: 'Total', propertyName: 'valorTotal', type: 'currency' },
  { name: 'Nit', propertyName: 'datosFacturacion.nit' },
  { name: 'Nombre Factura', propertyName: 'datosFacturacion.nombre' },
  { name: 'Fecha', propertyName: 'createdAt', type: 'date' }
]
const actionsTable = [{ name: 'Ver', path: '/venta/:id' }]
fetchAllSalesOfUser()
</script>
