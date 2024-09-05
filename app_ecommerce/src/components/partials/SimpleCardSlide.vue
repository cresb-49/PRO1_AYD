<template>
    <PartialSlide :components="cards" :titulo="titulo">
        <template v-slot:default="{ component }">
            <v-card class="mr-2" style="aspect-ratio: 1;" :to="component.path">
                <v-img height="150px" :src="component.imageSrc" cover></v-img>
                <v-card-title>
                    {{ component.text }}
                </v-card-title>
            </v-card>
        </template>
    </PartialSlide>
</template>
<script setup lang="ts">
import { type PropType } from 'vue';
import PartialSlide from './PartialSlide.vue'
type CardContent = {
    imageSrc: String,
    text: String
}
defineProps({
    cards: {
        type: Array<PropType<CardContent>>,
        required: true
    },
    titulo: {
        type: String,
        required: true
    },
})
</script>
