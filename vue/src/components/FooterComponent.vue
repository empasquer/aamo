<script setup lang="ts">
import { useRoute } from "vue-router";
import { computed } from "vue";
import IconButtonComponent from "./IconButtonComponent.vue";

const route = useRoute();
const isOmMigPage = computed(() => route.path.includes("om-mig"));
</script>

<template>
  <footer
    :class="{
      'w-screen grid grid-cols-3 bg-transparent justify-around py-8 items-center': true,
      'fixed bottom-0 left-0 w-full': isOmMigPage,
    }"
  >
    <p class="p-0 col-start-2">© Eva AAMO - {{ new Date().getFullYear() }}</p>
    <div class="flex p-0 col-start-3 place-content-end pr-10 gap-8">
      <IconButtonComponent variant="mail" height="25px"></IconButtonComponent>
      <IconButtonComponent variant="instagram" height="25px"></IconButtonComponent>
    </div>
  </footer>
</template>

<style scoped>
.fixed {
  position: absolute;
  bottom: -110vh;
  left: 0;
  width: 100%;
}
</style>
