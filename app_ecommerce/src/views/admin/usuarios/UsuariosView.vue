<template>
  <main>
    <header>
      <h1 class="mb-4">Usuarios</h1>
      <v-btn style="margin: 3px;" prepend-icon="mdi-arrow-left" to="/admin" class="mt-3"> Regresar </v-btn>
      <v-btn style="margin: 3px;" prepend-icon="mdi-pencil-outline" to="/admin/productos/add" class="mt-3">
        Agregar Usuario
      </v-btn>
    </header>
    <section>
      <TableData :columns="columns" :data="users" data_key="id" :actions="actionsTable" />
    </section>
  </main>
</template>
<script setup lang="ts">
import TableData from '../../../components/partials/TableData.vue'
import { storeToRefs } from 'pinia'
import { useUserStore } from '@/stores/users'

const { fetchAllUsers } = useUserStore()
const { users } = storeToRefs(useUserStore())

const consoleLog = (i: number) => {}

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
  { name: 'Permisos', path: '/admin/usuarios/permisos/:id' }
]

fetchAllUsers()
</script>
