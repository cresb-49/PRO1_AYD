<template>
    <v-row v-for="(row, index) in rows" :key="index">
        <v-col>
            <h3>{{ row.key }}</h3>
        </v-col>
        <v-col>
            {{ row.value }}
        </v-col>
        <v-divider v-if="rows.length > 0"></v-divider>
    </v-row>
</template>
<script setup lang="ts">
defineProps({
    rows: {
        type: Array<{key: any, value: any}>,
        required: true
    }
})
</script>