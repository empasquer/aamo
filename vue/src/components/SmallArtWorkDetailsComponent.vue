<script setup lang="ts">

interface ArtWork {
  artWorkId: number;
  title: string;
  description: string;
  price: number;
  media: {
    mediaUrl: string;
  };
}

defineProps<{
  artWork: ArtWork | null;
}>();

</script>

<template>
  <div v-if="artWork"
       class="absolute top-0 left-0 w-full h-full bg-black bg-opacity-70 text-white rounded shadow-lg p-4 flex flex-col justify-center items-center transition-opacity duration-300 opacity-100 pointer-events-none">
    <h3 class="text-lg font-bold mb-2">{{ artWork.title }}</h3>
    <p class="text-sm mb-2">{{ artWork.description }}</p>
    <p class="text-sm font-semibold">Price: {{ artWork.price }} DKK</p>
  </div>
</template>

<style scoped>

</style>