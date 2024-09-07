<template>
    <v-sheet>
    <h2>{{ titulo }}</h2>
      <v-slide-group
        show-arrows
      >
        <v-slide-group-item
          v-for="(component, index) in components" :key="index"
        >
            <slot :component="component" :index="index"></slot>
        </v-slide-group-item>
      </v-slide-group>
    </v-sheet>
</template>
<script setup lang="ts">
    defineProps({
        components: {
            type: Array<any>,
            required: true
        },
        titulo: {
            type: String,
            required: true
        },
    })
</script>
