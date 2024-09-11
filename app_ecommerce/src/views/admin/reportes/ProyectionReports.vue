<template>
  <main>
    <header class="mb-4">
      <h1 class="mb-4">Graficos</h1>
      <v-btn prepend-icon="mdi-arrow-left" to="/admin" class="mt-3"> Regresar </v-btn>
    </header>
    <section>
      <v-container>
        <v-row>
          <v-col cols="12" xs="8" sm="8" md="8" lg="6">
            <ProductosVendidosBarChart />
          </v-col>
          <v-col cols="12" xs="8" sm="8" md="8" lg="6">
            <ClientesFrecuentesBarChart />
          </v-col>
          <v-col cols="12" xs="8" sm="8" md="8" lg="6">
            <VentasVsImpuestos />
          </v-col>
        </v-row>
      </v-container>
    </section>
  </main>
</template>

<script setup lang="ts">
import ProductosVendidosBarChart from '@/components/charts/ProductosVendidosBarChart.vue'
import VentasVsImpuestos from '@/components/charts/VentasVsImpuestos.vue'
import ClientesFrecuentesBarChart from '@/components/charts/ClientesFrecuentesBarChart.vue'
</script>
