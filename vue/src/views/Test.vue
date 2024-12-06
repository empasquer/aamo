<script setup lang="ts">
import IconButtonComponent from "../components/IconButtonComponent.vue";
import FooterComponent from "../components/FooterComponent.vue";
import Headings from "../components/Headings.vue";
import LogoComponent from "../components/LogoComponent.vue";

async function fetchKommuner() {
  const response = await axios.get("http://localhost:8080/kommuner?regionskode=1084");
  console.log(response);
}

async function fecthArtworks() {
  const response = await axios.get("http://127.0.0.1:8080/artworks");
  console.log(response);
}
</script>

<template>
  <div>
    <LogoComponent width="h-[100px]"></LogoComponent>
    <button @click="fecthArtworks">Fetch Artworks</button>

    <div class="bg-blue-400 flex">
      <IconButtonComponent variant="instagram" height="25px" />
      <IconButtonComponent variant="mail" height="25px" />
      <IconButtonComponent variant="arrow-circle" height="25px" scrollToId="targetElement" />
    </div>

    <div class="bg-red-300">blablaaaa</div>
    <p class="p-4 m-10 border-2 text-red-900">hellloooo im styled with tailwindcss</p>

    <Headings :level="1" text="H1"></Headings>
    <Headings :level="2" text="H2"></Headings>
    <Headings :level="3" text="H3"></Headings>
    <div id="scroll-test" style="margin-top: 2000px">Scroll test</div>
    <FooterComponent></FooterComponent>
  </div>
</template>

<style scoped>
/* Your scoped styles */
</style>
