<script setup lang="ts">

import {computed} from "vue";

interface ArtWorkTag {
  tagType: string;
  tagValue: string;
}

interface ArtWork {
  artWorkId: number;
  title: string;
  description: string;
  price: number;
  media: {
    mediaUrl: string;
  };
  tags: ArtWorkTag[];
}

const props = defineProps<{
  artWork: ArtWork | null;
}>();

const sizeTag = computed(() => {
  return props.artWork?.tags.find(tag => tag.tagType === "SIZE")?.tagValue || null;
});

const emit = defineEmits(["click"]);

</script>

<template>
  <div v-if="artWork"
       class="absolute top-0 left-0 w-full h-full bg-black bg-opacity-60 text-white !important rounded shadow-lg p-4 flex flex-col justify-start items-start transition-opacity duration-300 opacity-100 pointer-events-none"
       @click="$emit('click', artWork)"
  >
    <h3 class="text-3xl font-bold tracking-wide text-white !important mb-2 text-left">
      {{ artWork.title ? `"${artWork.title}"` : 'Unavngivet' }}</h3>
    <p class="text-lg text-white !important font-bold mb-2 text-left" v-if="sizeTag">{{ sizeTag }}</p>
    <p class="text-lg text-white !important mb-2 mt-4 text-left">{{ artWork.description }}</p>
  </div>
</template>

<style scoped>
div {
  pointer-events: none; /* Klik går gennem denne komponent */
}

div:hover {
  pointer-events: auto; /* Gør komponenten klikbar ved hover */
}
</style>