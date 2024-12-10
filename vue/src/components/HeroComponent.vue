<script setup lang="ts">
import LogoComponent from "./LogoComponent.vue";
import HeadingsComponent from "./HeadingsComponent.vue";
</script>

<template>
  <section class="relative">
    <!-- Grid of divs with background images -->
    <div class="grid grid-cols-3 gap-6 relative top-[-1.7rem]">
      <div class="relative h-[75vh] bg-[url(./assets/hero-pics/1.jpeg)] bg-cover bg-center">
        <!-- Content inside each grid div can go here, if necessary -->
      </div>
      <div class="relative h-[75vh] bg-[url(./assets/hero-pics/2.jpeg)] bg-cover bg-center">
        <!-- Content inside each grid div can go here, if necessary -->
      </div>
      <div class="relative h-[75vh] bg-[url(./assets/hero-pics/3.jpeg)] bg-cover bg-center">
        <!-- Content inside each grid div can go here, if necessary -->
      </div>
    </div>

    <!-- Dark row overlaying the images -->
    <div class="absolute top-[30vh] left-0 w-full h-[35vh] bg-black bg-opacity-50 flex items-center justify-center z-10">
      <LogoComponent width="w-[70vw]" opacity="true" fill="white" />
      <HeadingsComponent :level="1" text="EVAAAMO" class="hidden"></HeadingsComponent>
    </div>
  </section>
</template>
