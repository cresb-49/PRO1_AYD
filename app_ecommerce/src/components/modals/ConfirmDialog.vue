<template>
  <v-dialog :model-value="isOpen" max-width="400" @update:model-value="updateDialog">
    <v-card>
      <v-card-title class="headline">{{ title }}</v-card-title>
      <v-card-text>{{ message }}</v-card-text>
      <v-card-actions>
        <v-btn color="red" @click="cancel">Cancelar</v-btn>
        <v-btn color="green" @click="confirm">Aceptar</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup lang="ts">
import { defineProps, defineEmits } from 'vue'

// Definir las propiedades que puede recibir el modal
interface Props {
  title: string
  message: string
  isOpen: boolean
  data?: any
}

const props: Props = defineProps({
  title: {
    type: String,
    default: 'Confirmar acción'
  },
  message: {
    type: String,
    default: '¿Estás seguro de continuar?'
  },
  isOpen: {
    type: Boolean,
    default: false
  },
  data: {
    type: Object,
    default: () => ({})
  }
})

// Emitir eventos de confirmación o cancelación
const emit = defineEmits<{
  (e: 'confirm', value: any): void
  (e: 'cancel', value: any): void
  (e: 'update:isOpen', value: boolean): void
}>()

// Función para cerrar el diálogo emitiendo el evento
const updateDialog = (value: boolean) => {
  emit('update:isOpen', value)
}

// Función para confirmar la acción
// Se envia la data del diálogo
const confirm = () => {
  emit('confirm', props.data)
  updateDialog(false) // Cerrar el diálogo
}

const cancel = () => {
  emit('cancel', props.data)
  updateDialog(false) // Cerrar el diálogo
}
</script>
