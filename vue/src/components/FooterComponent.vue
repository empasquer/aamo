<script setup lang="ts">
import IconButtonComponent from "./IconButtonComponent.vue";
</script>

<template>
  <footer class="w-screen grid grid-cols-3 bg-transparent justify-around py-8 items-center">
    <p class="p-0 col-start-2">© Eva AAMO - {{ new Date().getFullYear() }}</p>
    <div class="flex p-0 col-start-3 place-content-end pr-10 gap-8">
      <IconButtonComponent variant="mail" height="25px"></IconButtonComponent>
      <IconButtonComponent variant="instagram" height="25px"></IconButtonComponent>
    </div>
  </footer>
</template>

