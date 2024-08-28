<template>
    <v-card
      class="mx-auto"
      max-width="344"
      hover
      :to="path"
    >
      <v-card-item>
        <v-card-title>
          {{ props.title }}
        </v-card-title>
  
        <v-card-subtitle>
            {{ props.subtitle }}
        </v-card-subtitle>
      </v-card-item>
    </v-card>
</template>
<script setup lang="ts">
    const props = defineProps({
        title: {
            type: String,
            required: true
        },
        subtitle: {
            type: String,
            required: true
        },
        path: {
            type: String,
            default: '/'
        }
    })
</script>