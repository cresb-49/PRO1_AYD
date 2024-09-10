<template>
  <main>
    <header>
      <h1 style="margin-bottom: 5px;">Descripcion de la Venta</h1>
      <v-btn prepend-icon="mdi-arrow-left" to="/ventas" class="mt-3">
        Regresar
      </v-btn>
    </header>
    <section style="margin-top: 5px;">
      <div>
        <v-container>
          <v-row>
            <!-- Primera tarjeta -->
            <v-col cols="12" md="6">
              <v-card class="mx-auto" max-width="400">
                <v-card-title>
                  <h3>General</h3>
                </v-card-title>
                <v-card-text>
                  <v-row
                    v-for="(item, index) in ventaData"
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
            </v-col>

            <!-- Segunda tarjeta -->
            <v-col cols="12" md="6">
              <v-card class="mx-auto" max-width="400">
                <v-card-title>
                  <h3>Facturacion</h3>
                </v-card-title>
                <v-card-text>
                  <v-row
                    v-for="(item, index) in facturacionData"
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
            </v-col>
          </v-row>
        </v-container>
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

// Informacion de la venta
const ventaData = ref([] as any)
const facturacionData = ref([] as any)

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
      ventaData.value = [
        { title: 'ID', value: sale.value.id },
        { title: 'Fecha', value: new Date(sale.value.createdAt).toLocaleDateString() },
        {
          title: 'Valor Total',
          value: new Intl.NumberFormat('es-GT', { style: 'currency', currency: 'GTQ' }).format(
            sale.value.valorTotal
          )
        },
        {
          title: 'Impuesto Total',
          value: new Intl.NumberFormat('es-GT', { style: 'currency', currency: 'GTQ' }).format(
            sale.value.totalImpuestosPagados
          )
        },
        {
          title: 'Cantida Productos',
          value: new Intl.NumberFormat('es-GT', { style: 'currency', currency: 'GTQ' }).format(
            sale.value.cantidadProductos
          )
        }
      ]
      facturacionData.value = [
        { title: 'Nombre', value: sale.value.datosFacturacion.nombre },
        { title: 'NIT', value: sale.value.datosFacturacion.nit }
      ]
    })
})
</script>
