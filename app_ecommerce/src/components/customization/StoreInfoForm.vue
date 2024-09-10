<template>
  <section>
    <div>
      <h3 class="mb-4 text-overline">Información de la tienda</h3>
      <v-row>
        <v-col cols="6">
          <v-text-field
            v-model="nameModel"
            variant="solo"
            label="Nombre de la Tienda"
            required
          >
            <template #prepend>
              <v-icon icon="mdi-account-outline" size="small" />
            </template>
          </v-text-field>
        </v-col>
        <v-col cols="6">
          <v-text-field
            v-model="deliveryCostModel"
            variant="solo"
            label="Costo de Envio"
            required
          >
            <template #prepend>
              <v-icon icon="mdi-truck-delivery-outline" size="small" />
            </template>
          </v-text-field>
        </v-col>
        <v-col cols="12" sm="6" md="6" lg="6" xl="6">
          <v-text-field
            v-model="codModel"
            variant="solo"
            label="Costo de Pago contra Entrega"
            required
          >
            <template #prepend>
              <v-icon icon="mdi-cash-marker" size="small" />
            </template>
          </v-text-field>
        </v-col>
        <v-col cols="12" sm="6" md="6" lg="6" xl="6">
          <v-text-field
            v-model="addressModel"
            variant="solo"
            label="Direccion de la Tienda"
            required
          >
            <template #prepend>
              <v-icon icon="mdi-map-marker-outline" size="small" />
            </template>
          </v-text-field>
        </v-col>
      <v-col cols="12" class="d-flex justify-center">
        <v-btn min-width="50%" @click="save">Guardar</v-btn>
      </v-col>
        <v-col cols="6">
          <h1 class="mb-2 text-overline">Logo Actual</h1>
          <ImageCarousel :images="[logo]" is_direct_src></ImageCarousel>
        </v-col>
        <v-col cols="6">
          <h1 class="mb-2 text-overline">Elegir Nuevo Logo</h1>
          <ImagesUploadForm ref="imageForm" unique></ImagesUploadForm>
        </v-col>
      <v-col cols="12" class="d-flex justify-center">
        <v-btn min-width="50%" @click="saveImage">Actualizar imagen</v-btn>
      </v-col>
      </v-row>
    </div>
  </section>
</template>
<script setup lang="ts">
import ImagesUploadForm from '../forms/shared/ImagesUploadForm.vue';
import ImageCarousel from '../partials/ImageCarousel.vue';
import { storeToRefs } from 'pinia';
import { useConfigsStore } from '@/stores/config';
import { ref } from 'vue';


const {name, logo, deliveryCost, cod, address } = useConfigsStore()

const nameModel = ref(name)
const deliveryCostModel = ref(deliveryCost)
const codModel = ref(cod)
const addressModel = ref(address)

const imageForm = ref(null)

const emits = defineEmits(['save', 'saveImage'])

function save() {
    const newConfig = {
      name: nameModel.value,
      deliveryCost: deliveryCostModel.value,
      cod: codModel.value,
      address: addressModel.value,
    }
    emits('save', newConfig)
}

function saveImage() {
    if (imageForm.value.images instanceof File) {
      emits('saveImage', imageForm.value.images)
    }
}
</script>
<style lang="scss" scoped></style>
