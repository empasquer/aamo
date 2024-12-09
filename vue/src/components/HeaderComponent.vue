<script setup lang="ts">
import LogoComponent from "./LogoComponent.vue";
import NavigationComponent from "./NavigationComponent.vue";
import BurgerMenuComponent from "./BurgerMenuComponent.vue";

const props = defineProps<{
  width?: string;
  height?: string;
}>();
</script>

<template>
  <header class="w-screen fixed top-0 py-8 pl-8 flex justify-between items-center bg-[#FFFFFFD5] z-10">
    <LogoComponent opacity="false" width="w-[150px]" fill="#4a4a4a" />

    <div class="hidden md:block">
      <NavigationComponent />
    </div>

    <div class="block md:hidden pr-8">
      <BurgerMenuComponent />
    </div>
  </header>
</template>
