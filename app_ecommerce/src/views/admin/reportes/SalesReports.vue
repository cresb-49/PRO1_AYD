<template>
  <main>
    <header class="mb-4">
      <h1 class="mb-4">Reportes de Ventas</h1>
      <v-btn prepend-icon="mdi-arrow-left" to="/admin" class="mt-3"> Regresar </v-btn>
    </header>
    <section>
      <CardReveal
        title="Reporte de Ventas"
        content="Ventas en un intervalo de tiempo"
        :enable_text_content="false"
      >
        <template #actions>
          <v-sheet class="mx-auto" width="300">
            <v-form fast-fail @submit.prevent="accionReporteVentas">
              <v-text-field v-model="firstDate" label="Fecha de inicio" type="date"></v-text-field>
              <v-text-field v-model="lastDate" label="Fecha de fin" type="date"></v-text-field>
              <v-btn class="mt-2" type="submit" block>Descargar</v-btn>
            </v-form>
          </v-sheet>
        </template>
      </CardReveal>
    </section>
  </main>
</template>

<script setup lang="ts">
import CardReveal from '@/components/partials/CardReveal.vue'
import { ref } from 'vue'

const firstDate = ref('')
const lastDate = ref('')

const accionReporteVentas = () => {
  console.log('Descargando reporte...')
  console.log('fecha de inicio: ', firstDate.value, 'fecha de fin: ', lastDate.value)
}
</script>
