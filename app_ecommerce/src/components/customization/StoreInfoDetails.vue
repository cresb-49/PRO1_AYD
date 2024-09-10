<template>
  <section>
    <div>
      <h3 class="mb-4 text-overline">Información de la tienda</h3>
      <v-row>
        <v-col cols="6">
          <v-text-field
            v-model="name"
            readonly
            variant="solo"
            label="Nombre de la Tienda"
          >
            <template #prepend>
              <v-icon icon="mdi-account-outline" size="small" />
            </template>
          </v-text-field>
        </v-col>
        <v-col cols="6">
          <v-text-field
            v-model="deliveryCost"
            readonly
            variant="solo"
            label="Costo de Envio"
          >
            <template #prepend>
              <v-icon icon="mdi-truck-delivery-outline" size="small" />
            </template>
          </v-text-field>
        </v-col>
        <v-col cols="12" sm="6" md="6" lg="6" xl="6">
          <v-text-field
            v-model="cod"
            readonly
            variant="solo"
            label="Costo de Pago contra Entrega"
          >
            <template #prepend>
              <v-icon icon="mdi-cash-marker" size="small" />
            </template>
          </v-text-field>
        </v-col>
        <v-col cols="12" sm="6" md="6" lg="6" xl="6">
          <v-text-field
            v-model="address"
            readonly
            variant="solo"
            label="Direccion de la Tienda"
          >
            <template #prepend>
              <v-icon icon="mdi-map-marker-outline" size="small" />
            </template>
          </v-text-field>
        </v-col>
        <v-col cols="3"></v-col>
        <v-col cols="6">
          <h1 class="mb-2 text-overline">Logo Actual</h1>
          <ImageCarousel :images="[logo]" is_direct_src></ImageCarousel>
        </v-col>
        <v-col cols="3"></v-col>
      </v-row>
    </div>
  </section>
</template>
<script setup lang="ts">
import { useConfigsStore } from '@/stores/config';
import { storeToRefs } from 'pinia';
import ImageCarousel from '../partials/ImageCarousel.vue';
import ImagesUploadForm from '../forms/shared/ImagesUploadForm.vue';

const {name, logo, deliveryCost, cod, address } = storeToRefs(useConfigsStore())
</script>
