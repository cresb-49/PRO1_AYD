<template>
  <main>
    <header>
      <h1 class="mb-4">Productos</h1>
      <v-btn prepend-icon="mdi-arrow-left" to="/admin" class="mt-3"> Regresar </v-btn>
      <v-btn prepend-icon="mdi-pencil-outline" to="/admin/productos/add" class="mt-3">
        Agregar Producto
      </v-btn>
    </header>
    <section>
      <TableData :columns="columns" :data="products" data_key="id" :actions="actionsTable" />
    </section>
    <ConfirmDialog
      :isOpen="isDialogOpen"
      title="Confirmar Eliminación"
      message="¿Estás seguro de que deseas eliminar este producto?"
      @confirm="handleConfirm"
      @cancel="handleCancel"
      @update:isOpen="updateDialogState"
      :data="dataDialog"
    />
  </main>
</template>
<script setup lang="ts">
import TableData from '../../../components/partials/TableData.vue'
import ConfirmDialog from '@/components/modals/ConfirmDialog.vue'
import { storeToRefs } from 'pinia'
import { useProductStore } from '@/stores/products'
import { ref } from 'vue'

const productStore = useProductStore()
const { fetchAllProducts, deleteProduct } = productStore
const { products } = storeToRefs(productStore)

const isDialogOpen = ref(false)
const dataDialog = ref({} as any)

const openDialog = () => {
  isDialogOpen.value = true
}
const updateDialogState = (value: boolean) => {
  isDialogOpen.value = value
}

const handleConfirm = async (data: any) => {
  console.log(data)
  await productStore.deleteProduct(data.id_product)
  isDialogOpen.value = false
}
const handleCancel = () => {
  isDialogOpen.value = false
}

const consoleLog = (i: number) => {
  dataDialog.value = { id_product: i }
  openDialog()
}

const columns = [
  { name: 'Id', propertyName: 'id' },
  { name: 'Nombre', propertyName: 'nombre' },
  { name: 'Precio', propertyName: 'precio' },
  { name: 'Stock', propertyName: 'stock' },
  { name: 'Categoria', propertyName: 'categoria.nombre' }
]
const actionsTable = [
  { name: 'Editar', path: '/admin/productos/edit/:id' },
  { name: 'Eliminar', onClick: consoleLog }
]

fetchAllProducts()
</script>
