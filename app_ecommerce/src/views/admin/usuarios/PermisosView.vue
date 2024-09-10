<template>
  <v-container>
    <h2 class="mb-4">Permisos del Usuario</h2>
    <v-btn class="mb-4" prepend-icon="mdi-arrow-left" @click="$router.go(-1)"> Regresar </v-btn>
    <!-- Informacion del usuario -->
    <v-card class="mb-4">
      <v-card-title>
        <h3>{{ usuario.nombres }} {{ usuario.apellidos }}</h3>
        <p>{{ usuario.email }}</p>
      </v-card-title>
    </v-card>

    <!-- Tabla con encabezado fijo y barra de desplazamiento vertical para filas -->
    <div class="table-responsive">
      <div class="table-container">
        <v-table density="compact">
          <thead>
            <tr>
              <th class="text-left">ID</th>
              <th class="text-left">Nombre</th>
              <th class="text-left th-ruta">Ruta</th>
              <th class="text-left">Asignado</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(item, index) in permisosAsignables" :key="index">
              <td>{{ item.permiso.id }}</td>
              <td>{{ item.permiso.nombre }}</td>
              <td class="td-ruta">{{ item.permiso.ruta }}</td>
              <td>
                <v-switch
                  v-model="item.asignado"
                  :color="item.asignado ? 'green' : 'red'"
                  @change="handleChange(item)"
                ></v-switch>
              </td>
            </tr>
          </tbody>
        </v-table>
      </div>
    </div>

    <!-- Botón para enviar cambios -->
    <v-btn :disabled="!hasChanges" color="primary" class="mt-4" @click="enviarCambios">
      Guardar Cambios
    </v-btn>
  </v-container>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import {
  useUserStore,
  type Permiso,
  type UpdatePermisosPayload,
  type UserByPermiso,
  type UsuarioPermiso
} from '@/stores/users'

export type PermisoAsignable = {
  permiso: Permiso
  asignado: boolean
}

const permisosAsignables = ref([] as PermisoAsignable[])
const originalPermisos = ref([] as PermisoAsignable[]) // Guardar estado original
const hasChanges = ref(false) // Indicar si hubo cambios

const route = useRoute()
const router = useRouter()
const usuario = ref({} as UserByPermiso)

const { fetchUser, fetchAllPermisos, updatePermisosUser } = useUserStore()

onMounted(() => {
  fetchUser(route.params.id as unknown as number)
    .then((r) => r.data.value.data as UserByPermiso)
    .then((user) => {
      usuario.value = user

      // Si el usuario no es de tipo ayudante, redirigir a la vista de usuarios
      if (user.roles[0].rol.nombre !== 'AYUDANTE') {
        router.go(-1)
        return
      }

      // Solo después de cargar el usuario, cargamos los permisos
      fetchAllPermisos().then((r) => {
        const permisos = r.data.value.data as Permiso[]
        permisosAsignables.value = permisos.map((permiso) => {
          return {
            permiso: permiso,
            asignado: usuario.value.permisos
              ? usuario.value.permisos.some((ur: any) => ur.permiso.id === permiso.id)
              : false // Si no hay permisos, se asigna false
          }
        })
        // Guardar una copia del estado original
        originalPermisos.value = JSON.parse(JSON.stringify(permisosAsignables.value))
      })
    })
})

// Manejar cambios en el switch
const handleChange = (item: PermisoAsignable) => {
  // Verificar si ha cambiado el estado de "asignado" en comparación con el original
  hasChanges.value = permisosAsignables.value.some(
    (permiso, index) => permiso.asignado !== originalPermisos.value[index].asignado
  )
}

// Función para enviar los cambios
const enviarCambios = () => {
  const permisosModificados = permisosAsignables.value
    .filter((permiso) => permiso.asignado) // Solo los permisos con asignado en true
    .map((permiso) => ({
      id: permiso.permiso.id,
      nombre: permiso.permiso.nombre,
      ruta: permiso.permiso.ruta
    }))

  const payload = {
    idUsuario: usuario.value.id,
    permisos: permisosModificados
  } as UpdatePermisosPayload

  updatePermisosUser(payload)
    .then((r) => r.data.value.data as UserByPermiso)
    .then((user) => {
      usuario.value = user
      originalPermisos.value = JSON.parse(JSON.stringify(permisosAsignables.value))
      hasChanges.value = false
    })
    .catch((e) => {
      //dejar el estado original si hay un error
      permisosAsignables.value = JSON.parse(JSON.stringify(originalPermisos.value))
      hasChanges.value = false
    })
}
</script>

<style scoped>
/* Contenedor para hacer la tabla desplazable en pantallas pequeñas */
.table-responsive {
  overflow-x: auto;
}

/* Limitar la altura de la tabla para mostrar un máximo de 5 filas y habilitar la barra de desplazamiento */
.table-container {
  max-height: 320px; /* Ajusta el valor si es necesario para que se adapten 5 filas */
  overflow-y: auto;
  position: relative;
}

/* Hacer que el encabezado de la tabla sea fijo */
thead th {
  position: sticky;
  top: 0;
  background-color: #f4f4f4; /* Fondo del encabezado */
  z-index: 1;
}

.table {
  width: 100%;
  border-collapse: collapse;
}

th,
td {
  padding: 8px;
  border: 1px solid #ddd;
}

td {
  text-align: left;
}

/* Ocultar la columna "Ruta" en pantallas pequeñas */
@media (max-width: 768px) {
  th,
  td {
    font-size: 14px;
    padding: 6px;
  }

  th {
    font-size: 15px;
  }

  /* Ocultar la columna de "Ruta" */
  .th-ruta,
  .td-ruta {
    display: none;
  }
}
</style>
