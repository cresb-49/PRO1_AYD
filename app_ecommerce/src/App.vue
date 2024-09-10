<template>
  <main :style="{ backgroundColor: bgColor }">
    <component :is="$route.meta.layout || 'div'">
      <router-view />
    </component>
  </main> 
</template>
<script setup lang="ts">
import { useConfigsStore } from './stores/config';

  const {initCommerce} = useConfigsStore()
  initCommerce()
</script>
<style lang="scss">
/*
Styles used for page and layout transitions - Start
*-active classes control the transition duration and timing function
*-to and *-from classes control the transition state
*/
.page-enter-active,
.page-leave-active {
  transition: all 0.2s ease-in-out;
}
.page-leave-to,
.page-enter-from {
  opacity: 0;
  transform: translateX(0.5rem);
}

.layout-enter-active,
.layout-leave-active {
  transition: all 0.2s ease-in-out;
}

.layout-leave-to,
.layout-enter-from {
  opacity: 0;
  transform: translateX(0.5rem);
}
/*
Styles used for page and layout transitions - End
*/
@import '@/assets/styles/main.scss';
</style>
