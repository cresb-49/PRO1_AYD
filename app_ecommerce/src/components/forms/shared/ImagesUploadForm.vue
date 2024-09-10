<template>
  <v-card>
    <v-card-text>
      <ImageCarousel :images="images instanceof Array ? images : [images]"/>
    </v-card-text>
    <v-file-input
      class="ml-4 mr-4"
      v-model="images"
      :show-size="1000"
      label="File input"
      placeholder="Select your files"
      prepend-icon="mdi-paperclip"
      variant="outlined"
      counter
      :multiple="!unique"
    >
      <template v-slot:selection="{ fileNames }">
        <template v-for="(fileName, index) in fileNames" :key="fileName">
          <v-chip v-if="index < 2" class="me-2" size="small" label>
            {{ fileName }}
          </v-chip>

          <span v-else-if="index === 2" class="text-overline text-grey-darken-3 mx-2">
            +{{ images.length - 2 }} File(s)
          </span>
        </template>
      </template>
    </v-file-input>
  </v-card>
</template>
<script setup lang="ts">
import ImageCarousel from '@/components/partials/ImageCarousel.vue';
import { ref } from 'vue';

const images = ref([])

defineProps({
  unique: {
    type: Boolean,
    default: false
  }
})

defineExpose({images})
</script>