<template>
  <v-card class="mx-auto" max-width="300">
    <v-form>
      <v-text-field v-model="subtotal" label="Subtotal" readonly>
        <template v-slot:append-inner>Q</template>
      </v-text-field>
      <v-text-field v-model="subtotalImpuestos" label="Impuestos" readonly>
        <template v-slot:append-inner>Q</template>
      </v-text-field>
      <h4 class="ml-2">Opcion de Entrega</h4>
      <v-radio-group v-model="opcionEntrega" inline @change="updateDelivery">
        <v-radio label="Retiro en Tienda" value="0"></v-radio>
        <v-radio label="Envio a Domicilio" value="1"></v-radio>
      </v-radio-group>
      <v-text-field
        v-model="direccion"
        v-if="opcionEntrega == '1'"
        label="Direccion de Entrega"
        :required="opcionEntrega == '1'"
      />
      <v-text-field v-model="costoEnvio" label="Costo Envio" readonly>
        <template v-slot:append-inner>Q</template>
      </v-text-field>

      <h4 class="ml-2">Metodo de Pago</h4>
      <v-radio-group v-model="metodoPago" inline @change="updateDelivery">
        <v-radio label="Pago contra Entrega" value="0"></v-radio>
        <v-radio label="Tarjeta de Credito/Debito" value="1"></v-radio>
      </v-radio-group>

      <v-container v-if="metodoPago == '1'">
        <h4 class="mb-2">Informacion de Tarjeta</h4>
        <v-text-field
          v-model="numeroTarjeta"
          label="Numero de Tarjeta"
          :required="metodoPago == '1'"
        />
        <v-row>
          <v-col>
            <v-date-input
              label="Fecha de Vencimiento"
              variant="underlined"
              :required="metodoPago == '1'"
            ></v-date-input>
          </v-col>
          <v-col>
            <v-text-field v-model="cvvTarjeta" label="CVV" :required="metodoPago == '1'" />
          </v-col>
        </v-row>
      </v-container>

      <v-text-field v-model="total" label="Total" readonly>
        <template v-slot:append-inner>Q</template>
      </v-text-field>
      <h4 class="ml-2">NIT de Facturacion</h4>
      <v-switch
        class="ml-2"
        v-model="consumidorFinal"
        :color="consumidorFinal ? 'green' : 'red'"
        :label="consumidorFinal ? `${user?.nit ?? 'No tienes un NIT asociado'}` : 'Consumidor Final'"
      ></v-switch>
      <v-btn width="100%" prepend-icon="mdi-cart-outline" @click="comprar">Comprar</v-btn>
    </v-form>
  </v-card>
</template>

<script setup lang="ts">
import { useConfigsStore } from '@/stores/config'
import { storeToRefs } from 'pinia'
import { computed, ref } from 'vue'
import { useAuthStore } from '../../../stores/auth'
import { type User } from '../../../stores/regular-auth'

const { user } = storeToRefs(useAuthStore())

const props = defineProps({
  subtotalProp: {
    type: Number,
    default: 0
  },
  taxProp: {
    type: Number,
    default: 0
  }
})

const subtotal = computed(() => {
    return props.subtotalProp
})
const subtotalImpuestos = computed(() => {
    return props.taxProp
})
const costoEnvio = ref(0)
const opcionEntrega = ref('0')
const direccion = ref('')
const metodoPago = ref('0')
const numeroTarjeta = ref('')
const cvvTarjeta = ref('')
const total = computed(() => {
    return subtotal.value + subtotalImpuestos.value + costoEnvio.value;
})
const consumidorFinal = ref(true)

const emits = defineEmits(['buy'])

function comprar() {
  const attributesSale = {
    consumidorFinal: !consumidorFinal.value,
    retiroEnTienda: opcionEntrega.value == '0' ? true : false,
    pagoContraEntrega: metodoPago.value == '0' ? true : false,
    direccion: direccion.value
  }
  console.log('emite')
  emits('buy', attributesSale)
}

function updateDelivery() {
  if (opcionEntrega.value === '0') {
    costoEnvio.value = 0
  } else {
    const { deliveryCost } = storeToRefs(useConfigsStore())
    costoEnvio.value = deliveryCost.value
  }
}

</script>
