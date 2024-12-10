<script setup lang="ts">
import HeadingsComponent from "./HeadingsComponent.vue";
import ImageLinkButtonComponent from "./ImageLinkButtonComponent.vue";
</script>

<template>
  <HeadingsComponent level:2 text="Mine malerier"></HeadingsComponent>
  <section class="">
    <div class="grid md:grid-cols-4 gap-4 top-[-1.7rem] grid-cols-1">
      <div class="mx-8 h-[45vh] bg-[url(./assets/hero-pics/1.jpeg)] bg-cover bg-center"></div>
      <div class="mx-8 h-[45vh] bg-[url(./assets/hero-pics/2.jpeg)] bg-cover"></div>
      <div class="mx-8 h-[45vh] bg-[url(./assets/hero-pics/3.jpeg)] bg-cover bg-center"></div>
      <ImageLinkButtonComponent
        class="mx-8 h-[45vh] bg-cover bg-center"
        href="/galleri"
        text="Klik her for at se mere &rarr;"
        image="../../public/uploads/90x40/PmD5we8m.jpg"
      ></ImageLinkButtonComponent>
    </div>
  </section>
</template>
