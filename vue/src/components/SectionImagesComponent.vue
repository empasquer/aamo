<script setup lang="ts">
import HeadingsComponent from "./HeadingsComponent.vue";
import ImageLinkButtonComponent from "./ImageLinkButtonComponent.vue";
</script>

<template>
  <section class="md:mx-[3rem] mt-[15vh] md:mt-[20vh]">
    <HeadingsComponent class="text-left" :level="2" text="Mine malerier"></HeadingsComponent>
    <div class="grid md:grid-cols-4 gap-2 grid-cols-1">
      <div class="mx-8 md:mx-0 h-[45vh] bg-[url(./assets/hero-pics/1.jpeg)] bg-cover bg-center"></div>
      <div class="mx-8 md:mx-0 h-[45vh] bg-[url(./assets/hero-pics/2.jpeg)] bg-cover"></div>
      <div class="mx-8 md:mx-0 h-[45vh] bg-[url(./assets/hero-pics/3.jpeg)] bg-cover bg-center"></div>
      <ImageLinkButtonComponent
        class="mx-8 md:mx-0 h-[45vh] bg-cover bg-center"
        href="/galleri"
        text="Se mere &rarr;"
        image="../../public/uploads/90x40/PmD5we8m.jpg"
      ></ImageLinkButtonComponent>
    </div>
  </section>
</template>
