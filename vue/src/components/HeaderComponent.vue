<script setup lang="ts">
import { computed } from "vue"; // Import computed from Vue
import { useRouter } from "vue-router";
import LogoComponent from "./LogoComponent.vue";
import NavigationComponent from "./NavigationComponent.vue";
import BurgerMenuComponent from "./BurgerMenuComponent.vue";

const props = defineProps<{
  width?: string;
  height?: string;
}>();

const router = useRouter();

// Check if the current route is the front page
const isFrontPage = computed(() => router.currentRoute.value.path === "/");
</script>

<template>
  <header
    :class="[
      'w-screen fixed top-0 py-8 pl-8 flex justify-between items-center z-50',
      isFrontPage ? 'bg-[url(./assets/bg-op.png)] bg-contain' : 'bg-white bg-opacity-80',
    ]"
  >
    <LogoComponent opacity="false" width="w-[150px]" fill="#4a4a4a" />
    <div class="hidden md:block">
      <NavigationComponent />
    </div>

    <div class="block md:hidden pr-8 z-50">
      <BurgerMenuComponent />
    </div>
  </header>
</template>
