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
                <KeyValueList title="General" :rows="ventaData"/>
            </v-col>

            <!-- Segunda tarjeta -->
            <v-col cols="12" md="6">
                <KeyValueList title="Facturacion" :rows="facturacionData"/>
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
import KeyValueList from '@/components/partials/KeyValueList.vue'

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
        { key: 'ID', value: sale.value.id },
        { key: 'Fecha', value: new Date(sale.value.createdAt).toLocaleDateString() },
        {
          key: 'Valor Total',
          value: new Intl.NumberFormat('es-GT', { style: 'currency', currency: 'GTQ' }).format(
            sale.value.valorTotal
          )
        },
        {
          key: 'Impuesto Total',
          value: new Intl.NumberFormat('es-GT', { style: 'currency', currency: 'GTQ' }).format(
            sale.value.totalImpuestosPagados
          )
        },
        {
          key: 'Cantidad Productos',
          value: sale.value.cantidadProductos
        }
      ]
      facturacionData.value = [
        { key: 'Nombre', value: sale.value.datosFacturacion.nombre },
        { key: 'NIT', value: sale.value.datosFacturacion.nit }
      ]
    })
})
</script>
