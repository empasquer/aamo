<script lang="ts">
export default {
  name: "Carousel",
  props: {
    mediaUrls: {
      type: Array,
      required: true,
    },
  },
  data() {
    return {
      currentIndex: 0,
    };
  },
  computed: {
    currentMediaUrl() {
      // Access the mediaUrl property from the array of media URLs
      const media = this.mediaUrls[this.currentIndex];
      return media ? media.mediaUrl : ''; // Return the mediaUrl
    },
  },
  methods: {
    isYoutubeLink(url: string): boolean {
      // Check if the URL is a YouTube link
      return typeof url === "string" && (url.includes("youtube.com") || url.includes("youtu.be"));
    },
    getYoutubeEmbedUrl(url: string): string {
      // Extract YouTube video ID and construct the embed URL
      const videoId = url.match(/(?:v=|\/)([a-zA-Z0-9_-]{11})/);
      return videoId ? `https://www.youtube.com/embed/${videoId[1]}` : "";
    },
    next() {
      // Move to the next media item
      this.currentIndex = (this.currentIndex + 1) % this.mediaUrls.length;
    },
    prev() {
      // Move to the previous media item
      this.currentIndex = (this.currentIndex - 1 + this.mediaUrls.length) % this.mediaUrls.length;
    },
  },
};
</script>

<template>
  <div class="flex justify-center items-center relative w-full">
    <!-- Previous Button -->
    <button @click="prev" class="absolute left-0 text-2xl cursor-pointer p-2">
      <i class="fa-solid fa-chevron-left"></i>
    </button>

    <!-- Current Media -->
    <div class="flex-1 text-center">
      <template v-if="isYoutubeLink(currentMediaUrl)">
        <!-- Embed YouTube Video -->
        <iframe
            :src="getYoutubeEmbedUrl(currentMediaUrl)"
            class="max-w-full h-auto"
            allowfullscreen
        ></iframe>
      </template>
      <template v-else>
        <!-- Display Image -->
        <img :src="currentMediaUrl" alt="Media" class="max-w-full h-auto" />
      </template>
    </div>

    <!-- Next Button -->
    <button @click="next" class="absolute right-0 text-2xl cursor-pointer p-2">
      <i class="fa-solid fa-chevron-right"></i>
    </button>
  </div>
</template>

<style scoped>
/* Scoped styles for Carousel component */
</style>
