<script setup lang="ts">
import { ref, onMounted, onUnmounted } from "vue";

const props = defineProps<{
  mailAddress: string;
}>();

const isRetracted = ref(false);

onMounted(() => {
  const handleScroll = () => {
    if (window.scrollY > 50) {
      isRetracted.value = true;
    } else {
      isRetracted.value = false;
    }
  };

  window.addEventListener("scroll", handleScroll);

  onUnmounted(() => {
    window.removeEventListener("scroll", handleScroll);
  });
});
</script>

<template>
  <!-- You can add this "transition-all ease-in-out duration-100" to the <a class etc> section for transition -->
  <a
    :href="'mailto:' + props.mailAddress"
    class="fixed right-0 bottom-[6vh] md:bottom-[12vh] flex items-center justify-center cursor-pointer bg-[#4289a3] bg-opacity-80 z-50"
    :class="{
      'w-10 h-10': isRetracted,
      'w-10 h-[25vh]': !isRetracted,
    }"
    @mouseover="isRetracted = false"
    @mouseleave="isRetracted = true"
  >
    <div v-if="!isRetracted" class="flex flex-col items-center justify-around w-full h-full text-center">
      <i class="fas fa-envelope text-white text-xl mb-2"></i>
      <span class="text-xl text-white writing-mode-vertical-lr text-orientation-mixed rotate-180">Kontakt mig</span>
    </div>
    <div v-if="isRetracted" class="flex items-center justify-center">
      <i class="fas fa-envelope text-white text-xl"></i>
    </div>
  </a>
</template>
