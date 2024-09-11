<template>
  <v-card :class="reveal ? 'expanded' : ''" class="mx-auto" max-width="344">
    <v-card-text>
      <div v-if="context">{{ context }}</div>
      <p class="text-h5 font-weight-black">{{ title }}</p>
      <p v-if="subtitle">{{ subtitle }}</p>
      <div class="text-medium-emphasis mt-2" v-if="content">
        {{ content }}
      </div>
    </v-card-text>

    <v-card-actions>
      <v-btn color="teal-accent-4" variant="text" @click="toggleReveal">
        <!-- Cambiar el texto del botón dinámicamente -->
        {{ reveal ? close_text : spand_text }}
      </v-btn>
    </v-card-actions>

    <v-expand-transition>
      <div v-if="reveal">
        <template v-if="enable_text_content">
          <v-card-text>
            <slot name="text-content" />
          </v-card-text>
        </template>
        <v-card-actions class="pt-0">
          <slot name="actions" />
        </v-card-actions>
      </div>
    </v-expand-transition>
  </v-card>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { en } from 'vuetify/locale';

const props = defineProps({
  context: {
    type: String,
    default: null
  },
  title: {
    type: String,
    required: true
  },
  subtitle: {
    type: String,
    default: null
  },
  content: {
    type: String,
    default: 'Contenido por defecto'
  },
  spand_text: {
    type: String,
    default: 'Ver más'
  },
  close_text: {
    type: String,
    default: 'Cerrar'
  },
  enable_text_content: {
    type: Boolean,
    default: true
  }
})

const reveal = ref(false)

// Función para alternar la visibilidad
const toggleReveal = () => {
  reveal.value = !reveal.value
}
</script>

<style scoped>
/* Ajustes para cuando el componente está expandido */
.expanded {
  max-height: 500px; /* Ajusta el tamaño máximo cuando está expandido */
  transition: max-height 0.3s ease-in-out;
}
</style>
