<template>
  <div>
    <v-card class="mx-auto" width="500" max-width="500" min-width="300">
      <template v-slot:title>
        <h3>Ventas Acumuladas (Gráfico de Área)</h3>
      </template>
      <v-card-text class="bg-surface-light pt-4">
        <v-container>
          <v-col>
            <v-row>
              <Line :data="data" :options="options" />
            </v-row>
            <v-row>
              <!--Fomulario para la seleccion de la fecha 1 y 2 de forma vertical-->
              <v-container class="mx-auto" width="500">
                <v-form fast-fail @submit.prevent="obtenerReporteVentas">
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
      </v-card-text>
    </v-card>
  </div>
</template>

<script setup lang="ts">
import { useReportStore, type SaleReport } from '@/stores/report'
import { storeToRefs } from 'pinia'
import { Line } from 'vue-chartjs'
import { onMounted, ref } from 'vue'

const reportStore = useReportStore()
const { fetchReportVentas } = reportStore
const { error, loading } = storeToRefs(reportStore)

const resultObtenerReporteVentas = ref({} as SaleReport)

const fecha1 = ref(getTodayDateInUTC6())
const fecha2 = ref(getTodayDateInUTC6())

onMounted(() => {
  obtenerReporteVentas()
})

async function obtenerReporteVentas() {
  const { data, error } = await fetchReportVentas({ fecha1: fecha1.value, fecha2: fecha2.value })
  console.log(data.value.data as SaleReport)
}

import {
  Chart as ChartJS,
  LineElement,
  CategoryScale,
  LinearScale,
  PointElement,
  Tooltip,
  Legend
} from 'chart.js'

ChartJS.register(LineElement, CategoryScale, LinearScale, PointElement, Tooltip, Legend)
const data = ref({
  labels: ['10/09/2024', '11/09/2024'], // Fechas de ventas
  datasets: [
    {
      label: 'Total Ventas',
      borderColor: '#42A5F5',
      backgroundColor: 'rgba(66, 165, 245, 0.5)',
      fill: true,
      data: [10, 15] // Total de ventas por fecha
    }
  ]
})
const options = ref({
  responsive: true,
  maintainAspectRatio: true
})
// Funcion de la fecha
function getTodayDateInUTC6() {
  const today = new Date()
  // Obtenemos la fecha en formato YYYY-MM-DD
  const year = today.getFullYear()
  const month = String(today.getMonth() + 1).padStart(2, '0') // Meses de 0-11
  const day = String(today.getDate()).padStart(2, '0')
  return `${year}-${month}-${day}`
}
</script>
