<template>
  <div>
    <v-card class="mx-auto" width="500" max-width="500" min-width="300">
      <template v-slot:title>
        <h4>Productos Vendidos</h4>
      </template>
      <v-card-text class="bg-surface-light pt-4">
        <v-container>
          <v-col>
            <v-row>
              <Bar id="my-chart-id" :options="barChartOptions" :data="chartData" />
            </v-row>
            <v-row>
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
import { onMounted, ref } from 'vue'
import { Bar } from 'vue-chartjs'
import { type ReportPayload } from '@/stores/report'
ChartJS.register(BarElement, CategoryScale, LinearScale, Tooltip, Legend)
import {
  Chart as ChartJS,
  BarElement,
  CategoryScale,
  LinearScale,
  Tooltip,
  Legend,
  plugins,
  scales
} from 'chart.js'
import { getRandomColor } from './colors'

const reportStore = useReportStore()
const { fetchReportVentas } = reportStore
const { error, loading } = storeToRefs(reportStore)

const resultObtenerReporteVentas = ref({} as SaleReport)

const fecha1 = ref(getTodayDateInUTC6())
const fecha2 = ref(getTodayDateInUTC6())

async function obtenerReporteVentas() {
  const { data, error } = await fetchReportVentas({ fecha1: fecha1.value, fecha2: fecha2.value })
  resultObtenerReporteVentas.value = data.value.data as SaleReport
  calcularDatos()
}

async function calcularDatos() {
  // Creamos un diccionario para almacenar los productos vendidos
  let productosVendidos = {} as { [key: string]: number }
  resultObtenerReporteVentas.value.productosVendidos.forEach((producto) => {
    if (productosVendidos[producto.producto]) {
      productosVendidos[producto.producto] += producto.cantidad
    } else {
      productosVendidos[producto.producto] = producto.cantidad
    }
  })
  // Convertimos el diccionario a un array de labels y data para el gráfico
  const labels = Object.keys(productosVendidos)
  const data = Object.values(productosVendidos)
  
  // En base a la cantidad de productos vendidos, se asigna un color a cada barra
  let anterior = null
  const colors = data.map(() => {
    const color = getRandomColor(anterior)
    return color
  })
  chartData.value = {
    labels,
    datasets: [
      {
        data,
        backgroundColor: colors
      }
    ]
  }
}
onMounted(() => {
  obtenerReporteVentas()
})

const chartData = ref({
  labels: ['January', 'February', 'March'],
  datasets: [
    {
      data: [40, 20, 12],
      backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56'] // Colores personalizados para cada barra
    }
  ]
})

const barChartOptions = ref({
  responsive: true,
  maintainAspectRatio: true,
  plugins: {
    legend: {
      display: false
    }
  },
  scales: {
    x: {
      title: {
        display: true,
        text: 'Producto' // Título del eje X
      }
    },
    y: {
      title: {
        display: true,
        text: 'Cantidad de Vendidos' // Título del eje Y
      },
      beginAtZero: true // Para comenzar el eje Y en 0
    }
  }
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
