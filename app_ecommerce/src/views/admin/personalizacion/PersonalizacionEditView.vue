<template>
  <main>
    <header class="mb-4">
      <h1>Personalizacion Tienda - Editar</h1>
      <v-btn prepend-icon="mdi-arrow-left" to="/admin/store" class="mt-3">
        Regresar
      </v-btn>
    </header>
    <section class="profile-edit-section">
      <StoreInfoForm @save="updateStore"></StoreInfoForm>
    </section>
  </main>
</template>
<script setup lang="ts">
import StoreInfoForm from '@/components/customization/StoreInfoForm.vue';
import { useConfigsStore, type UpdatePayload } from '@/stores/config';

const {updateConfig} = useConfigsStore()

function updateStore(params: {name: string, deliveryCost: string, cod: string, address: string}) {
  const updateParams: UpdatePayload = {
    nombreTienda: params.name,
    costoEnvio: params.deliveryCost,
    precioPagoContraEntrega: params.cod,
    direccionEmpresa: params.address
  }
  updateConfig(updateParams)
}
</script>
<style lang="scss" scoped>
.profile-edit-section {
  margin-bottom: 1.5rem;
}
</style>
