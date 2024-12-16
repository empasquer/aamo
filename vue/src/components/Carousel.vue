<script lang="ts">
import { ref, computed } from "vue";

export default {
  name: "Carousel",
  props: {
    mediaUrls: {
      type: Array,
      required: true,
    },
  },
  setup(props) {
    const currentIndex = ref(0);

    const currentMediaUrl = computed(() => {
      const media = props.mediaUrls[currentIndex.value];
      return media ? media.mediaUrl : ""; // Return the mediaUrl
    });

    const isYoutubeLink = (url: string): boolean => {
      return typeof url === "string" && (url.includes("youtube.com") || url.includes("youtu.be"));
    };

    const getYoutubeEmbedUrl = (url: string): string => {
      const videoId = url.match(/(?:v=|\/)([a-zA-Z0-9_-]{11})/);
      return videoId ? `https://www.youtube.com/embed/${videoId[1]}` : "";
    };

    const next = () => {
      currentIndex.value = (currentIndex.value + 1) % props.mediaUrls.length;
    };

    const prev = () => {
      currentIndex.value = (currentIndex.value - 1 + props.mediaUrls.length) % props.mediaUrls.length;
    };

    return {
      currentMediaUrl,
      isYoutubeLink,
      getYoutubeEmbedUrl,
      next,
      prev,
    };
  },
};
</script>

<template>
  <div class="grid grid-cols-[auto_70vw_auto] items-center w-[90vw]" v-touch:swipeleft="next" v-touch:swiperight="prev">
    <button @click="prev" class="p-0 py-2 text-2xl bg-transparent cursor-pointer justify-self-start z-10">
      <i class="fa-solid fa-chevron-left"></i>
    </button>

    <div class="text-center w-full relative z-0">
      <template v-if="isYoutubeLink(currentMediaUrl)">
        <iframe :src="getYoutubeEmbedUrl(currentMediaUrl)" class="w-full h-auto" allowfullscreen frameborder="0"></iframe>
      </template>
      <template v-else>
        <img :src="currentMediaUrl" alt="Media" class="w-full h-auto" />
      </template>
    </div>

    <button @click="next" class="p-0 py-2 text-2xl bg-transparent cursor-pointer justify-self-end z-10">
      <i class="fa-solid fa-chevron-right"></i>
    </button>
  </div>
</template>
