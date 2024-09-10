<template>
  <main>
    <header>
      <h1 class="mb-4">Usuarios</h1>
      <v-btn style="margin: 3px" prepend-icon="mdi-arrow-left" to="/admin" class="mt-3">
        Regresar
      </v-btn>
      <v-btn
        style="margin: 3px"
        prepend-icon="mdi-pencil-outline"
        to="/admin/user/add"
        class="mt-3"
      >
        Agregar Usuario
      </v-btn>
      <v-btn
        style="margin: 3px"
        prepend-icon="mdi-pencil-outline"
        to="/admin/admin/add"
        class="mt-3"
      >
        Agregar Admin
      </v-btn>
      <v-btn
        style="margin: 3px"
        prepend-icon="mdi-pencil-outline"
        to="/admin/ayudante/add"
        class="mt-3"
      >
        Agregar Ayudante
      </v-btn>
    </header>
    <section>
      <TableData :columns="columns" :data="users" data_key="id" :actions="actionsTable" />
    </section>
    <ConfirmDialog
      :isOpen="isDialogOpen"
      title="Confirmar Eliminación"
      message="¿Estás seguro de que deseas eliminar este usuario?"
      @confirm="handleConfirm"
      @cancel="handleCancel"
      @update:isOpen="updateDialogState"
      :data="dataDialog"
    />
  </main>
</template>
<script setup lang="ts">
import TableData from '../../../components/partials/TableData.vue'
import { storeToRefs } from 'pinia'
import { useUserStore } from '@/stores/users'
import ConfirmDialog from '@/components/modals/ConfirmDialog.vue'
import { ref } from 'vue'

const userStore = useUserStore()
const { fetchAllUsers, deleteUser } = userStore
const { users } = storeToRefs(userStore)

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
  await userStore.deleteUser(data.id_user)
  isDialogOpen.value = false
  await userStore.fetchAllUsers()
}
const handleCancel = () => {
  isDialogOpen.value = false
}

const consoleLog = (i: number) => {
  dataDialog.value = { id_user: i }
  openDialog()
}

const columns = [
  { name: 'Id', propertyName: 'id' },
  { name: 'Nombres', propertyName: 'nombres' },
  { name: 'Apellidos', propertyName: 'apellidos' },
  { name: 'Email', propertyName: 'email' },
  { name: 'Rol', propertyName: 'roles.0.rol.nombre' }
]
const actionsTable = [
  { name: 'Editar', path: '/admin/usuarios/edit/:id' },
  { name: 'Eliminar', onClick: consoleLog },
  { name: 'Permisos', path: '/admin/usuario/permisos/:id' }
]

fetchAllUsers()
</script>
