<template>
    <v-card class="mx-auto" max-width="300">
        <v-text-field v-model="subtotal" label="Subtotal" />
        <v-radio-group v-model="opcionEntrega" inline>
            <v-radio label="Retiro en Tienda" value="0"></v-radio>
            <v-radio label="Envio a Domicilio" value="1"></v-radio>
        </v-radio-group>
        <v-text-field v-model="costoEnvio" label="Costo Envio" />
        <v-text-field v-model="total" label="Total" />
        <v-btn>Comprar</v-btn>
    </v-card>
</template>
<script setup lang="ts">
import { ref } from 'vue';

const props = defineProps({
    subtotalProp: {
        type: Number,
        default: 0
    },
})

const subtotal = ref(props.subtotalProp)
const costoEnvio = ref(0)
const opcionEntrega = ref("0")
const total = ref(subtotal.value)
</script>