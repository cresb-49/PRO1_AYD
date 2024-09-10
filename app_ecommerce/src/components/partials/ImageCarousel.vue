<template>
  <v-carousel v-model:model-value="controlActiveIndex" hide-delimiter-background :show-arrows="images.length > 1">
    <v-carousel-item v-for="(image, index) in images" :key="index" width="1000"
      :src="is_direct_src ? image : base64Images[index]" aspect-ratio="1" cover>
    </v-carousel-item>
  </v-carousel>
</template>
<script setup lang="ts">
import { ref, watchEffect } from 'vue'

const props = defineProps({
  images: {
    type: Array,
    required: true
  },
  is_direct_src: {
    type: Boolean,
    default: false
  }
})

const base64Images = ref(new Array())
const controlActiveIndex = ref(0)

watchEffect(() => {
  base64Images.value = []

  if (!props.is_direct_src) {
    props.images.forEach((image, index) => {
      if (image !== undefined) {
        const reader = new FileReader()
        reader.readAsDataURL(image as Blob)

        reader.onload = (e) => {
          base64Images.value[index] = e.target!.result as string
          if (index === 0) {
            controlActiveIndex.value = 0
          }
        }
      }
    })
  }
})
</script>
