<template>
  <main>
    <header>
      <h1>Descripcion de la Venta</h1>
    </header>
    <section>
      <div>
        <v-card class="mx-auto" max-width="400">
          <v-card-title class="text-h5">General</v-card-title>
          <!-- <v-card-subtitle>Informacion</v-card-subtitle> -->
          <v-card-text>
            <v-row
              v-for="(item, index) in userData"
              :key="index"
              class="d-flex justify-space-between"
            >
              <v-col cols="6" class="text-left font-weight-bold">
                {{ item.title }}
              </v-col>
              <v-col cols="6" class="text-right">
                {{ item.value }}
              </v-col>
            </v-row>
          </v-card-text>
        </v-card>
      </div>
      <div>
        <h2>Datos de Facturacion</h2>
      </div>
      <div>
        <h2>Productos</h2>
        <TableData
          :columns="columnsProductos"
          :data="sale.lineaVentas"
          data_key="producto.id"
          :actions="actionsTableProductos"
        />
      </div>
    </section>
  </main>
</template>

<script setup lang="ts">
import TableData from '@/components/partials/TableData.vue'
import { storeToRefs } from 'pinia'
import { useSalesStore, type Sale, type SaleLine } from '@/stores/sales'
import { onMounted, ref } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()
const { fetchSaleById } = useSalesStore()
const sale = ref({} as Sale)

const userData = [
  { title: 'Nombre', value: 'Juan Pérez' },
  { title: 'Correo Electrónico', value: 'juan.perez@example.com' },
  { title: 'Teléfono', value: '+502 1234-5678' },
  { title: 'Dirección', value: 'Zona 1, Guatemala' }
]

const consoleLog = (i: number) => {}

// Informacion de la tabla de productos
const columnsProductos = [
  { name: 'Id', propertyName: 'producto.id' },
  { name: 'Nombre', propertyName: 'producto.nombre' },
  { name: 'Precio de Compra', propertyName: 'precio', type: 'currency' },
  { name: 'Precio Actual', propertyName: 'producto.precio', type: 'currency' },
  { name: 'Impuesto Pagado', propertyName: 'impuestoPagado', type: 'currency' },
  { name: 'Cantidad', propertyName: 'cantidad' }
]
const actionsTableProductos = [
  { name: 'Ver', path: '/producto/:producto.id', property: 'producto.id' }
]

onMounted(() => {
  fetchSaleById(route.params.id as unknown as number)
    .then((r) => r.data.value.data as Sale)
    .then((s) => {
      sale.value = s
    })
})
</script>
