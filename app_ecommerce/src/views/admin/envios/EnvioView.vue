<template>
    <main>
        <header class="mb-4">
            <h1 class="mb-4">Orden {{ order?.id }}</h1>
            <v-btn prepend-icon="mdi-arrow-left" to="/admin/envios" class="mt-3">
                Regresar
            </v-btn>
        </header>
        <section>
            <v-row>
                <v-col>
                    <KeyValueList title="Informacion del Envio" :rows="infoOrder"></KeyValueList>
                </v-col>
                <v-col>
                    <h1>Opciones</h1>
                    <v-div v-if="order?.estadoEnvio.nombre === 'PENDIENTE'">
                        <h4>El pedido aun no ha sido entregado</h4>
                        <v-btn width="100%" @click="entregarPedido">Entregar Pedido</v-btn>
                    </v-div>
                    <h4 v-else>El pedido ya ha sido entregado!</h4>
                </v-col>
            </v-row>
        </section>
    </main>
</template>
<script setup lang="ts">
import KeyValueList from '@/components/partials/KeyValueList.vue';
import { useOrderStore, type Order } from '@/stores/orders';
import { ref } from 'vue';
import { useRoute } from 'vue-router';

const { fetchOrder, deliverOrder } = useOrderStore()

const order = ref<Order>()

const infoOrder = ref<any>()

function entregarPedido() {
    deliverOrder(order.value?.id as number).then(r => r.data.value.data as Order).then(o => {
        order.value = o;
        infoOrder.value = [
            {key: 'Direccion', value: order.value?.direccion},
            {key: 'Venta', value: order.value?.venta.id},
            {key: 'Nombre', value: order.value?.venta.datosFacturacion.nombre},
            {key: 'NIT', value: order.value?.venta.datosFacturacion.nit},
            {key: 'Estado', value: order.value?.estadoEnvio.nombre}
        ]
    })
}

fetchOrder(useRoute().params.id as unknown as number).then(r => r.data.value.data as Order).then(o => {
    order.value = o;
    infoOrder.value = [
        {key: 'Direccion', value: order.value?.direccion},
        {key: 'Venta', value: order.value?.venta.id},
        {key: 'Nombre', value: order.value?.venta.datosFacturacion.nombre},
        {key: 'NIT', value: order.value?.venta.datosFacturacion.nit},
        {key: 'Estado', value: order.value?.estadoEnvio.nombre}
    ]
})

</script>