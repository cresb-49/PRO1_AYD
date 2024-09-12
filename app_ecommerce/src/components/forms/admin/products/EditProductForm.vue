<template>
    <v-row>
        <v-col cols="12" xs="8" sm="12" md="6" lg="6" xl="6">
            <ImageCarousel :images="images" :is_direct_src="true" />
        </v-col>
        <v-col cols="12" xs="3" sm="12" md="6" lg="6" xl="6">
            <v-card class="login-card pa-8 mt-4">
                <v-card-text>
                    <v-form>
                        <v-text-field v-model="nombreProducto" label="Nombre Producto" type="text" required
                            :disabled="loading" prepend-icon="mdi-rename-outline">
                        </v-text-field>
                        <v-select v-model="categoria" label="Categoria" :disabled="loading"
                            prepend-icon="mdi-tournament" :items="categories" item-title="nombre" item-value="id">
                        </v-select>
                        <v-text-field v-model="precio" label="Precio" density="compact" type="number" variant="outlined"
                            hide-details prepend-icon="mdi-cash"></v-text-field>
                        <v-text-field v-model="impuesto" label="Porcentaje de Impuesto" density="compact" type="number" variant="outlined"
                            hide-details prepend-icon="mdi-cash" append-inner-icon="mdi-percent" class="mt-4"></v-text-field>
                        <v-text-field v-model="stock" label="Stock Actual" density="compact" type="number"
                            variant="outlined" hide-details disabled prepend-icon="mdi-abacus"
                            class="mt-4"></v-text-field>
                    </v-form>
                </v-card-text>
                <v-card-actions>
                    <v-row>
                        <v-col cols="12">
                            <v-btn variant="tonal" width="100%" :loading="loading" @click="update"> Guardar </v-btn>
                        </v-col>
                    </v-row>
                </v-card-actions>
            </v-card>
        </v-col>
    </v-row>
</template>
<script setup lang="ts">
import { useCategoryStore } from '@/stores/categories'
import { storeToRefs } from 'pinia'
import { ref } from 'vue'
import ImageCarousel from '@/components/partials/ImageCarousel.vue';
import { useProductStore, type Product } from '@/stores/products';

const props = defineProps({
    loading: {
        type: Boolean,
        default: false
    },
    product_id: {
        type: Number,
        required: true
    }
})
const emits = defineEmits(['update'])

const nombreProducto = ref('')
const categoria = ref()
const precio = ref(0)
const impuesto = ref(0)
const stock = ref(0)

const images = ref({})

const { categories } = storeToRefs(useCategoryStore())
const { fetchProduct } = useProductStore()

fetchProduct(props.product_id).then(r => r.data.value.data as Product).then(c => {
    nombreProducto.value = c.nombre;
    categoria.value = c.categoria;
    precio.value = c.precio
    stock.value = c.stock
    impuesto.value = c.porcentajeImpuesto
    images.value = c.imagenesUrls?.map(image => { return image.replace('//:', '://') })
});

function update() {
    const newAttributes = {
        id: props.product_id,
        nombre: nombreProducto.value.trim(),
        categoria: categoria.value,
        precio: precio.value,
        stock: stock.value,
        impuesto: impuesto.value
    }
    emits('update', newAttributes)
}
</script>