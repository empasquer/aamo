<script setup lang="ts">
import { ref, computed } from "vue";
import VueTouch from "vue3-touch-events";

// Props
interface Media {
  mediaUrl: string;
}

const props = defineProps<{
  mediaUrls: Media[];
}>();

// Reactive state
const currentIndex = ref(0);

// Computed property for the current media URL
const currentMediaUrl = computed(() => {
  const media = props.mediaUrls[currentIndex.value];
  return media ? media.mediaUrl : "";
});

// Methods
function isYoutubeLink(url: string): boolean {
  return typeof url === "string" && (url.includes("youtube.com") || url.includes("youtu.be"));
}

function getYoutubeEmbedUrl(url: string): string {
  const videoId = url.match(/(?:v=|\/)([a-zA-Z0-9_-]{11})/);
  return videoId ? `https://www.youtube.com/embed/${videoId[1]}` : "";
}

function next(): void {
  currentIndex.value = (currentIndex.value + 1) % props.mediaUrls.length;
}

function prev(): void {
  currentIndex.value = (currentIndex.value - 1 + props.mediaUrls.length) % props.mediaUrls.length;
}
</script>

<template>
  <div
      class="flex justify-center items-center relative w-full sm:w-[80%]"
      v-touch:swipeleft="next"
      v-touch:swiperight="prev"
  >
    <!-- Previous Button -->
    <button
        @click="prev"
        class="absolute -left-16 sm:left-6 text-2xl border-0 bg-transparent cursor-pointer"
    >
      <i class="fa-solid fa-chevron-left"></i>
    </button>

    <!-- Current Media -->
    <div class="text-center sm:w-1/2">
      <template v-if="isYoutubeLink(currentMediaUrl)">
        <!-- Embed YouTube Video -->
        <iframe
            :src="getYoutubeEmbedUrl(currentMediaUrl)"
            class="w-full h-auto"
            allowfullscreen
        ></iframe>
      </template>
      <template v-else>
        <!-- Display Image -->
        <img :src="currentMediaUrl" alt="Media" class="w-full h-auto" />
      </template>
    </div>

    <!-- Next Button -->
    <button
        @click="next"
        class="absolute -right-16 sm:right-6 bg-transparent border-0 text-2xl cursor-pointer"
    >
      <i class="fa-solid fa-chevron-right"></i>
    </button>
  </div>
</template>

<style scoped>
/* Scoped styles for Carousel component */
</style>
