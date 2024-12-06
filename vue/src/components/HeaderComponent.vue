<script setup lang="ts">
import LogoComponent from "./LogoComponent.vue";
import NavigationComponent from "./NavigationComponent.vue";

const props = defineProps<{
  width?: string;
  height?: string;
}>();
</script>

<template>
  <header class="w-screen fixed top-0 py-8 pl-8 flex justify-between bg-white z-10">
    <LogoComponent width="w-[150px]" fill="#4a4a4a"></LogoComponent>
    <NavigationComponent />
  </header>
</template>
