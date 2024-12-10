<script setup lang="ts">
import LogoComponent from "./LogoComponent.vue";
import HeadingsComponent from "./HeadingsComponent.vue";
import IconButtonComponent from "./IconButtonComponent.vue";

const props = defineProps({
  scrollToId: {
    type: String,
    required: true,
  },
});
</script>

<template>
  <section class="relative">
    <div class="grid md:grid-cols-3 gap-6 relative top-[-1.7rem] grid-cols-1">
      <div class="relative h-[75vh] bg-[url(./assets/hero-pics/1.jpeg)] bg-cover bg-center hidden md:block"></div>
      <div class="relative md:h-[75vh] bg-[url(./assets/hero-pics/2.jpeg)] bg-cover bg-[40%_center] h-screen w-full"></div>
      <div class="relative h-[75vh] bg-[url(./assets/hero-pics/3.jpeg)] bg-cover bg-center hidden md:block"></div>
    </div>

    <div class="absolute top-[40vh] md:top-[30vh] left-0 w-full h-[15vh] md:h-[35vh] bg-black bg-opacity-50 flex items-center justify-center z-10">
      <LogoComponent width="w-[70vw]" opacity="true" fill="white" />
      <HeadingsComponent :level="1" text="EVAAAMO" class="hidden"></HeadingsComponent>
    </div>
    <div class="absolute top-[80vh] md:top-[60vh] left-0 w-full h-[15vh] md:h-[35vh] flex items-center justify-center z-20">
      <IconButtonComponent color="white" variant="arrow-circle" scroll-to-id="props.scrollToId" height="10vh"></IconButtonComponent>
    </div>
  </section>
</template>
