<template>
    <v-container>
        <v-row>
            <v-col v-for="(card, index) in cards" :key="index" cols="12" sm="6" md="4" lg="3">
                <v-card class="mx-auto" max-width="300" :to="card.path">
                    <v-img :src="(card.image ?? 'https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/No_image_available.svg/600px-No_image_available.svg.png')" height="150px"></v-img>

                    <v-card-title>{{ card.name }}</v-card-title>
                </v-card>
            </v-col>
        </v-row>
    </v-container>
</template>

<script setup lang="ts">
    defineProps({
        cards: {
            type: Array<any>,
            required: true
        }
    })
</script>

<style scoped>
/* Optional styling */
</style>