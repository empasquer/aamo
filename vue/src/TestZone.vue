<script setup lang="ts">
import axios from "axios";
import IconButtonComponent from "./components/IconButtonComponent.vue";

async function fetchKommuner() {
  const response = await axios.get("http://localhost:8080/kommuner?regionskode=1084");
  console.log(response);
}

async function fecthArtworks() {
  const response = await axios.get("http://127.0.0.1:8080/artworks");
  console.log(response)
}
</script>

<template>
  <div>
    <button @click="fecthArtworks">Fetch Artworks</button>

    <IconButtonComponent variant="instagram" height="h-[40px]" width="h-[40px]"></IconButtonComponent>
    <IconButtonComponent variant="mail" height="h-[40px]" width="h-[40px]"></IconButtonComponent>
    <IconButtonComponent variant="arrow-circle" scroll-to-id="scroll-test" height="h-[40px]" width="h-[40px]"></IconButtonComponent>
    <div class="bg-red-300">blablaaaa</div>

    <p class="p-4 m-10 border-2 text-red-900">hellloooo im styled with tailwindcss</p>

    <div id="scroll-test" style="margin-top: 2000px">Scroll test</div>
  </div>
</template>

<style scoped></style>
