<template>
    <v-card class="mx-auto" max-width="400">
        <v-card-title>
            <h3>{{ title }}</h3>
        </v-card-title>
        <v-card-text>
            <v-row v-for="(row, index) in rows" :key="index" class="d-flex justify-space-between">
                <v-col cols="6" class="text-left font-weight-bold">
                    {{ row.key }}
                </v-col>
                <v-col cols="6" class="text-right">
                    {{ row.value }}
                </v-col>
            </v-row>
        </v-card-text>
    </v-card>
</template>
<script setup lang="ts">
defineProps({
    title: {
        type: String,
        required: true
    },
    rows: {
        type: Array<{ key: any, value: any }>,
        required: true
    }
})
</script>