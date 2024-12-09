<script setup lang="ts">
import axios from "axios";
import HeaderComponent from "./components/HeaderComponent.vue";
import FooterComponent from "./components/FooterComponent.vue";

// async function fetchKommuner() {
//   const response = await axios.get("http://localhost:8080/kommuner?regionskode=1084");
//   console.log(response);
// }
</script>

<template>
  <HeaderComponent></HeaderComponent>
  <RouterView class="pt-[calc(93px+2rem)]" />
  <FooterComponent></FooterComponent>
</template>

<style scoped></style>
