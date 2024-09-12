<template>
  <v-row>
    <v-col cols="12" xs="8" sm="12" md="6" lg="6" xl="6">
      <ImageCarousel :images="producto
        ? producto.imagenesUrls
          ? producto.imagenesUrls.map((img) => {
            return img.replace('//:', '://')
          })
          : []
        : []
        " :is_direct_src="true" />
    </v-col>
    <v-divider vertical></v-divider>
    <v-col cols="12" xs="3" sm="12" md="6" lg="6" xl="6">
      <v-card class="login-card pa-8 mt-4">
        <v-card-title>
          <h4>{{ producto ? producto.categoria.nombre : '' }}</h4>
        </v-card-title>
        <v-divider></v-divider>
        <v-card-text>
          <v-form>
            <h1>{{ producto ? producto.nombre : '' }}</h1>
            <v-row>
              <v-col>
                <h1>Precio</h1>
              </v-col>
              <v-col>
                <h1 style="font-size: xx-large">Q.{{ producto ? producto.precio : 0 }}</h1>
              </v-col>
            </v-row>
            <h4 v-if="producto" class="mt-4">
              {{
                producto.stock > 0
                  ? producto.stock > 5
                    ? 'En Stock'
                    : `Solo quedan ${producto.stock} en stock`
                  : 'No Disponible'
              }}
            </h4>
            <v-select class="mt-2" v-model="cantidad" label="Cantidad" density="compact" type="number"
              variant="outlined" :items="producto
                ? producto.stock > 5
                  ? quantityRange(5)
                  : quantityRange(producto.stock)
                : quantityRange(1)
                " hide-details prepend-icon="mdi-abacus"></v-select>
          </v-form>
        </v-card-text>
        <v-card-actions>
          <v-row>
            <v-col v-if="role == 'regular'" cols="12">
              <v-btn variant="tonal" width="100%" @click="addProduct" prepend-icon="mdi-cart"
                v-if="producto ? !enCarrito : true">
                Agregar al carrito
              </v-btn>
              <v-btn variant="tonal" width="80%" prepend-icon="mdi-cart" disabled v-if="producto ? enCarrito : false">
                En Carrito
              </v-btn>
              <v-btn variant="tonal" class="ml-2" color="error" v-if="producto ? enCarrito : true" @click="removeProduct">
                <v-icon>mdi-trash-can-outline</v-icon>
              </v-btn>
            </v-col>
            <v-col v-if="role != 'regular' && producto && role!== null" cols="12">
              <v-btn variant="tonal" width="100%" :to="`/admin/productos/edit/${producto.id}`" prepend-icon="mdi-pen">
                Editar Producto
              </v-btn>
            </v-col>
          </v-row>
        </v-card-actions>
      </v-card>
    </v-col>
  </v-row>
</template>
<script setup lang="ts">
import { ref, type PropType } from 'vue'
import ImageCarousel from '@/components/partials/ImageCarousel.vue'
import { type Product } from '@/stores/products'
import { useAuthStore } from '@/stores/auth'
import { useCartStore } from '@/stores/cart'

const { role } = useAuthStore()
const { isProductInCart } = useCartStore()

const props = defineProps({
  producto: {
    type: Object as PropType<Product> | undefined,
    required: true
  }
})
const emits = defineEmits(['addToCart', 'removeFromCart'])

const enCarrito = ref(false)
const cantidad = ref(1)

defineExpose({
  enCarrito
})

function quantityRange(n: number) {
  return Array.from({ length: n }, (_, i) => i + 1)
}

function addProduct() {
  const cartData = {
    cantidad: cantidad
  }
  emits('addToCart', cartData)
}

function removeProduct() {
  emits('removeFromCart')
}
</script>
