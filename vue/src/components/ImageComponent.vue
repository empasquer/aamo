<script setup lang="ts">

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

defineProps<{
  artWork: ArtWork;
}>();

const emit = defineEmits(["hover"]);
</script>


<template>
  <div
      class="relative cursor-pointer group"
      @mouseenter="$emit('hover', artWork)"
      @mouseleave="$emit('hover', null)"
  >
    <img
        :src="artWork.media.mediaUrl"
        :alt="artWork.title || 'Art Work'"
        class="w-full h-auto rounded shadow-md border border-gray-300"
    />
  </div>


</template>

<style scoped>

</style>