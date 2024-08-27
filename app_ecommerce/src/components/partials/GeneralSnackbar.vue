<template>
    <v-snackbar
      v-model="snackbarShow"
      vertical
      location="top center"
      rounded="xl"
    >
      <div
        class="text-overline pb-2 font-weight-bold d-flex align-center"
        :class="[colorSnackbar]"
      >
        <v-icon class="mr-2">
          {{ iconSnackbar }}
        </v-icon>
        {{ title }}
      </div>
  
      <p>{{ message }}</p>
  
      <template #actions>
        <v-btn color="white" @click="hideSnackbar"> Cerrar </v-btn>
      </template>
    </v-snackbar>
</template>
<script lang="ts">
  import { mapWritableState, mapActions } from 'pinia'
  import { useSnackbarStore, SnackbarType } from '../../stores/snackbar'
  export default {
    computed: {
      ...mapWritableState(useSnackbarStore, {
        snackbarShow: 'snackbarShow',
        title: 'title',
        message: 'message',
        color: 'color',
        type: 'type'
      }),
      iconSnackbar() {
        switch (this.type) {
          case SnackbarType.ERROR:
            return 'mdi-alert-octagon-outline'
          case SnackbarType.SUCCESS:
            return 'mdi-check-circle-outline'
          case SnackbarType.WARNING:
            return 'mdi-alert-outline'
          case SnackbarType.MESSAGE:
            return 'mdi-information-outline'
          default:
            return 'mdi-information-outline'
        }
      },
      colorSnackbar() {
        switch (this.type) {
          case SnackbarType.ERROR:
            return 'text-red-lighten-1'
          case SnackbarType.SUCCESS:
            return 'text-teal'
          case SnackbarType.WARNING:
            return 'text-yellow'
          case SnackbarType.MESSAGE:
            return 'text-cyan'
          default:
            return 'text-white'
        }
      }
    },
    methods: {
      ...mapActions(useSnackbarStore, ['showSnackbar', 'hideSnackbar'])
    }
  }
</script>