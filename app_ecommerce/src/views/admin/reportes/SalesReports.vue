<template>
  <main>
    <header class="mb-4">
      <h1 class="mb-4">Reportes de Ventas</h1>
      <v-btn prepend-icon="mdi-arrow-left" to="/admin" class="mt-3"> Regresar </v-btn>
    </header>
    <section>
      <v-row>
        <v-col cols="12" xs="8" sm="4" md="4" lg="4">
          <CardReveal
            title="Reporte de Ventas"
            content="Ventas en un intervalo de tiempo"
            :enable_text_content="false"
          >
            <template #actions>
              <v-sheet class="mx-auto" width="300">
                <v-form fast-fail @submit.prevent="accionReporteVentas">
                  <v-text-field
                    v-model="firstDate"
                    label="Fecha de inicio"
                    type="date"
                  ></v-text-field>
                  <v-text-field v-model="lastDate" label="Fecha de fin" type="date"></v-text-field>
                  <v-btn class="mt-2" type="submit" block>Descargar</v-btn>
                </v-form>
              </v-sheet>
            </template>
          </CardReveal>
        </v-col>
        <v-col cols="12" xs="8" sm="4" md="4" lg="4">
          <CardReveal
            title="Reporte de Pedidos"
            content="Pedidos en un intervalo de tiempo"
            :enable_text_content="false"
          >
            <template #actions>
              <v-sheet class="mx-auto" width="300">
                <v-form fast-fail @submit.prevent="accionReportePedidos">
                  <v-text-field
                    v-model="firstDatePedidos"
                    label="Fecha de inicio"
                    type="date"
                  ></v-text-field>
                  <v-text-field
                    v-model="lastDatePedidos"
                    label="Fecha de fin"
                    type="date"
                  ></v-text-field>
                  <v-btn class="mt-2" type="submit" block>Descargar</v-btn>
                </v-form>
              </v-sheet>
            </template>
          </CardReveal>
        </v-col>
        <v-col cols="12" xs="8" sm="4" md="4" lg="4">
          <CardReveal
            title="Reporte Inventario"
            content="Reporte de los productos de la plataforma"
            :enable_text_content="false"
          >
            <template #actions>
              <v-sheet class="mx-auto" width="300">
                <p>Informacion de los productos de la tienda</p>
                <v-btn class="mt-2" block @click="accionReporteInventario">Descargar</v-btn>
              </v-sheet>
            </template>
          </CardReveal>
        </v-col>
        <v-col cols="12" xs="8" sm="4" md="4" lg="4">
          <CardReveal
            title="Reporte Clientes"
            content="Reporte de los clientes que mas compran en la tienda"
            :enable_text_content="false"
          >
            <template #actions>
              <v-sheet class="mx-auto" width="300">
                <p>Informacion de los clientes que mas compran en la tienda</p>
                <v-btn class="mt-2" block @click="accionReporteClientes">Descargar</v-btn>
              </v-sheet>
            </template>
          </CardReveal>
        </v-col>
        <v-col cols="12" xs="8" sm="4" md="4" lg="4"> </v-col>
        <v-col cols="12" xs="8" sm="4" md="4" lg="4"> </v-col>
      </v-row>
    </section>
  </main>
</template>

<script setup lang="ts">
import CardReveal from '@/components/partials/CardReveal.vue'
import { ref } from 'vue'

const firstDate = ref('')
const lastDate = ref('')

const firstDatePedidos = ref('')
const lastDatePedidos = ref('')

const accionReporteVentas = () => {
  console.log('Descargando reporte...')
  console.log('fecha de inicio: ', firstDate.value, 'fecha de fin: ', lastDate.value)
}

const accionReportePedidos = () => {
  console.log('Descargando reporte...')
  console.log('fecha de inicio: ', firstDatePedidos.value, 'fecha de fin: ', lastDatePedidos.value)
}

const accionReporteInventario = () => {
  console.log('Descargando reporte...')
}

const accionReporteClientes = () => {
  console.log('Descargando reporte...')
}
</script>
