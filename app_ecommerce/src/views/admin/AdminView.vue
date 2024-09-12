<template>
  <main>
    <h1 class="mb-4">Administracion</h1>
    <v-row>
      <h2>Tienda</h2>
    </v-row>
    <v-row>
      <v-col v-if="role!='helper'" cols="12" xs="8" sm="4" md="4" lg="4">
        <menu-option-card
          title="Categorias"
          subtitle="Administracion de las Categorias"
          path="/admin/categorias"
        ></menu-option-card>
      </v-col>
      <v-col cols="12" xs="8" sm="4" md="4" lg="4">
        <menu-option-card
          title="Productos"
          subtitle="Administracion de los Productos"
          path="/admin/productos"
        ></menu-option-card>
      </v-col>
      <v-col cols="12" xs="8" sm="4" md="4" lg="4">
        <menu-option-card
          title="Inventario"
          subtitle="Administracion del Inventario"
          path="/admin/inventario"
        ></menu-option-card>
      </v-col>
      <v-col cols="12" xs="8" sm="4" md="4" lg="4">
        <menu-option-card
          title="Envios"
          subtitle="Administracion de Envios/Orders"
          path="/admin/envios"
        ></menu-option-card>
      </v-col>

    </v-row>
    <v-divider class="mt-4 mb-6"></v-divider>
    <v-row v-if="role!='helper'">
      <h2>Reportes</h2>
    </v-row>
    <v-row>
      <v-col  cols="12" xs="8" sm="4" md="4" lg="4">
        <menu-option-card
          title="Reportes Varios"
          subtitle="Visualizacion de Reportes del sistema"
          path="/admin/reportes"
        ></menu-option-card>
      </v-col>
      <v-col v-if="role!='helper'" cols="12" xs="8" sm="4" md="4" lg="4">
        <menu-option-card
          title="Graficos"
          subtitle="Visualizacion de graficos del sistema"
          path="/admin/reportes/graficos"
        ></menu-option-card>
      </v-col>
    </v-row>
    <v-divider class="mt-4 mb-6"></v-divider>
    <v-row v-if="role!='helper'">
      <h2>Sistema</h2>
    </v-row>
    <v-row>
      <v-col v-if="role!='helper'" cols="12" xs="8" sm="4" md="4" lg="4">
        <menu-option-card
          title="Usuarios"
          subtitle="Administracion de Usuarios administrativos"
          path="/admin/usuarios"
        ></menu-option-card>
      </v-col>
      <v-col v-if="role!='helper'" cols="12" xs="8" sm="4" md="4" lg="4">
        <menu-option-card
          title="Personalizacion"
          subtitle="Personalizacion de logo y nombre de la tienda"
          path="/admin/store"
        ></menu-option-card>
      </v-col>
      <v-col> </v-col>
    </v-row>
  </main>
</template>

<script setup lang="ts">
import MenuOptionCard from '../../components/partials/MenuOptionCard.vue'
import { useAuthStore } from '@/stores/auth'

const authStore = useAuthStore();
const { role, user, logout } = authStore;

</script>