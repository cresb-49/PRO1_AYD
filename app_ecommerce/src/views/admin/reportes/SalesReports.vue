<template>
  <main>
    <header class="mb-4">
      <h1 class="mb-4">Reportes de Ventas</h1>
      <v-btn prepend-icon="mdi-arrow-left" to="/admin" class="mt-3"> Regresar </v-btn>
    </header>
    <section>
      <v-row>
        <v-col cols="12" xs="8" sm="6" md="4" lg="4">
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
                  <v-select
                    v-model="tipoReporteVenta"
                    label="Tipo de Reporte"
                    :items="tiposReporteVenta"
                  >
                  </v-select>
                  <v-btn class="mt-2" type="submit" block>Descargar</v-btn>
                </v-form>
              </v-sheet>
            </template>
          </CardReveal>
        </v-col>
        <v-col cols="12" xs="8" sm="6" md="4" lg="4">
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
                  <v-select
                    v-model="tipoReportePedidos"
                    label="Tipo de Reporte"
                    :items="tiposReporteVenta"
                  ></v-select>
                  <v-btn class="mt-2" type="submit" block>Descargar</v-btn>
                </v-form>
              </v-sheet>
            </template>
          </CardReveal>
        </v-col>
        <v-col cols="12" xs="8" sm="6" md="4" lg="4">
          <CardReveal
            title="Reporte Inventario"
            content="Reporte de los productos de la plataforma"
            :enable_text_content="false"
          >
            <template #actions>
              <v-sheet class="mx-auto" width="300">
                <v-select
                  v-model="tipoReporteVenta"
                  label="Tipo de Reporte"
                  :items="tiposReporteVenta"
                ></v-select>
                <v-btn class="mt-2" block @click="accionReporteInventario">Descargar</v-btn>
              </v-sheet>
            </template>
          </CardReveal>
        </v-col>
        <v-col cols="12" xs="8" sm="6" md="4" lg="4">
          <CardReveal
            title="Reporte Clientes"
            content="Reporte de los clientes que mas compran en la tienda"
            :enable_text_content="false"
          >
            <template #actions>
              <v-sheet class="mx-auto" width="300">
                <v-select
                  v-model="tipoReporteVenta"
                  label="Tipo de Reporte"
                  :items="tiposReporteVenta"
                ></v-select>
                <v-btn class="mt-2" block @click="accionReporteClientes">Descargar</v-btn>
              </v-sheet>
            </template>
          </CardReveal>
        </v-col>
      </v-row>
    </section>
  </main>
</template>

<script setup lang="ts">
import {
  type ReportExportPayload,
  ReportExportType,
  ReportType,
  useReportStore
} from '@/stores/report'
import CardReveal from '@/components/partials/CardReveal.vue'
import { ref } from 'vue'

const reportStore = useReportStore()
const { fetchReport } = reportStore

const firstDate = ref(getTodayDateInUTC6())
const lastDate = ref(getTodayDateInUTC6())
const tipoReporteVenta = ref(ReportExportType.PDF)

const tiposReporteVenta = [ReportExportType.PDF, ReportExportType.EXCEL, ReportExportType.WORD]

const firstDatePedidos = ref(getTodayDateInUTC6())
const lastDatePedidos = ref(getTodayDateInUTC6())
const tipoReportePedidos = ref(ReportExportType.PDF)

function getTodayDateInUTC6() {
  const today = new Date()
  // Obtenemos la fecha en formato YYYY-MM-DD
  const year = today.getFullYear()
  const month = String(today.getMonth() + 1).padStart(2, '0') // Meses de 0-11
  const day = String(today.getDate()).padStart(2, '0')
  return `${year}-${month}-${day}`
}

const accionReporteVentas = () => {
  console.log('Descargando reporte...')
  const payload: ReportExportPayload = {
    fecha1: firstDate.value,
    fecha2: lastDate.value,
    tipoExporte: tipoReporteVenta.value,
    tipoReporte: ReportType.VENTAS
  }
  console.log(payload)
}

const accionReportePedidos = () => {
  console.log('Descargando reporte...')
  const payload: ReportExportPayload = {
    fecha1: firstDatePedidos.value,
    fecha2: lastDatePedidos.value,
    tipoExporte: ReportExportType.PDF,
    tipoReporte: ReportType.PEDIDOS
  }
  console.log(payload)
}

const accionReporteInventario = () => {
  console.log('Descargando reporte...')
  const payload: ReportExportPayload = {
    tipoExporte: ReportExportType.PDF,
    tipoReporte: ReportType.INVENTARIO
  }
  console.log(payload)
}

const accionReporteClientes = () => {
  console.log('Descargando reporte...')
  const payload: ReportExportPayload = {
    tipoExporte: ReportExportType.PDF,
    tipoReporte: ReportType.CLIENTES_FRECUENTES
  }
  console.log(payload)
}
</script>
