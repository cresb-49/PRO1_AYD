<template>
  <div>
    <v-card class="mx-auto" width="500" max-width="500" min-width="300">
      <template v-slot:title>
        <h4>Ventas vs Impuestos vs Entregas</h4>
      </template>
      <v-card-text class="bg-surface-light pt-4">
        <v-container>
          <v-container>
            <v-col>
              <v-row>
                <Doughnut :data="doughnutChartData" :options="doughnutChartOptions" />
              </v-row>
              <v-row>
                <v-container class="mx-auto" width="500">
                  <v-form fast-fail @submit.prevent="obtenerReporteInventario">
                    <v-row>
                      <v-col cols="6">
                        <v-text-field
                          v-model="fecha1"
                          label="Fecha de inicio"
                          type="date"
                          dense
                        ></v-text-field>
                      </v-col>
                      <v-col cols="6">
                        <v-text-field
                          v-model="fecha2"
                          label="Fecha de fin"
                          type="date"
                          dense
                        ></v-text-field>
                      </v-col>
                      <v-col cols="12">
                        <v-btn type="submit" block small>Actualizar Gráfico</v-btn>
                      </v-col>
                    </v-row>
                  </v-form>
                </v-container>
              </v-row>
            </v-col>
          </v-container>
        </v-container>
      </v-card-text>
    </v-card>
  </div>
</template>

<script setup lang="ts">
import { useReportStore, type SaleReport } from '@/stores/report'
import { storeToRefs } from 'pinia'
import { onMounted, ref } from 'vue'
import { Bar } from 'vue-chartjs'
import { type ReportPayload } from '@/stores/report'
ChartJS.register(ArcElement, CategoryScale, LinearScale, Tooltip, Legend)
import {
  Chart as ChartJS,
  ArcElement,
  CategoryScale,
  LinearScale,
  Tooltip,
  Legend,
  Colors
} from 'chart.js'
import { Doughnut } from 'vue-chartjs'
import { ColorsChart } from './colors'

const reportStore = useReportStore()
const { fetchReporteInventario } = reportStore
const { error, loading } = storeToRefs(reportStore)

const resultObtenerReporteVentas = ref({} as SaleReport)

const fecha1 = ref(getTodayDateInUTC6())
const fecha2 = ref(getTodayDateInUTC6())

async function obtenerReporteInventario() {
  const { data, error } = await fetchReporteInventario({})
  resultObtenerReporteVentas.value = data.value.data as SaleReport
  calcularDatos()
}

async function calcularDatos() {
  const data = [
    resultObtenerReporteVentas.value.total,
    resultObtenerReporteVentas.value.totalImpuestoPagado,
    resultObtenerReporteVentas.value.totalPagoEntrega
  ]
  doughnutChartData.value = {
    labels: ['Total Ventas', 'Impuestos Pagados', 'Entregas'],
    datasets: [
      {
        label: 'Ventas vs Impuestos',
        backgroundColor: [ColorsChart.Magenta, ColorsChart.Red, ColorsChart.Lime],
        data
      }
    ]
  }
}

onMounted(() => {
  obtenerReporteInventario()
})

const doughnutChartData = ref({
  labels: ['Total Ventas', 'Impuestos Pagados', 'Entregas'], // Etiquetas
  datasets: [
    {
      label: 'Ventas vs Impuestos',
      backgroundColor: [ColorsChart.Magenta, ColorsChart.Red, ColorsChart.Lime],
      data: [0, 0, 0] // Total ventas e impuestos
    }
  ]
})

const doughnutChartOptions = ref({
  responsive: true,
  maintainAspectRatio: true
})

function getTodayDateInUTC6() {
  const today = new Date()
  // Obtenemos la fecha en formato YYYY-MM-DD
  const year = today.getFullYear()
  const month = String(today.getMonth() + 1).padStart(2, '0') // Meses de 0-11
  const day = String(today.getDate()).padStart(2, '0')
  return `${year}-${month}-${day}`
}
</script>
