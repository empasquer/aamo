<script setup lang="ts">
import { computed } from "vue";
import { useRoute } from "vue-router";

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
  isLoggedIn: boolean;
}>();

const sizeTag = computed(() => {
  return props.artWork?.tags.find(tag => tag.tagType === "SIZE")?.tagValue || null;
});

const emit = defineEmits(["deleteArtWork", "openModal"]);

const route = useRoute();
const isAdminRoute = route.path.startsWith("/admin");
</script>

<template>
  <div class="relative group cursor-pointer">
    <!-- Artwork Image -->
    <img
        v-if="artWork"
        :src="artWork.media.mediaUrl"
        :alt="artWork.title || 'Art Work'"
        class="w-full h-auto rounded shadow-md border border-gray-300"
        @click="$emit('openModal', artWork)"
    />

    <!-- Artwork Details (visible on hover) -->
    <div
        v-if="artWork"
        class="absolute top-0 left-0 w-full h-full bg-black bg-opacity-60 text-white shadow-lg p-4 flex flex-col justify-start items-start transition-opacity duration-300 opacity-0 group-hover:opacity-100 pointer-events-none"
    >
      <h3 class="text-3xl font-bold tracking-wide text-white mb-2 text-left">
        {{ artWork.title ? `"${artWork.title}"` : 'Unavngivet' }}
      </h3>
      <p class="text-lg text-white font-bold mb-2 text-left" v-if="sizeTag">
        {{ sizeTag }}
      </p>
      <p class="text-lg text-white mb-2 mt-4 text-left description">
        {{ artWork.description }}
      </p>

      <!-- Trashcan button, only visible if logged in and URL starts with /admin -->
      <button
          v-if="isLoggedIn && isAdminRoute"
          style="pointer-events: auto;"
          @click.stop="$emit('deleteArtWork', artWork.artWorkId, artWork.title)"
          class="absolute bottom-2 right-2 bg-red-700 text-white p-2 border-none cursor-pointer hover:bg-red-800"
      >
        <svg
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 24 24"
            stroke-width="1.5"
            stroke="#EAEAEA"
            class="w-6 h-6 text-red-400 hover:text-red-800"
        >
          <path
              stroke-linecap="round"
              stroke-linejoin="round"
              d="m14.74 9-.346 9m-4.788 0L9.26 9m9.968-3.21c.342.052.682.107 1.022.166m-1.022-.165L18.16 19.673a2.25 2.25 0 0 1-2.244 2.077H8.084a2.25 2.25 0 0 1-2.244-2.077L4.772 5.79m14.456 0a48.108 48.108 0 0 0-3.478-.397m-12 .562c.34-.059.68-.114 1.022-.165m0 0a48.11 48.11 0 0 1 3.478-.397m7.5 0v-.916c0-1.18-.91-2.164-2.09-2.201a51.964 51.964 0 0 0-3.32 0c-1.18.037-2.09 1.022-2.09 2.201v.916m7.5 0a48.667 48.667 0 0 0-7.5 0"
          />
        </svg>
      </button>

      <!-- Edit button, only visible if logged in and URL starts with /admin -->
      <button
          v-if="isLoggedIn && isAdminRoute"
          style="pointer-events: auto;"
          @click.stop="$emit('deleteArtWork', artWork.artWorkId, artWork.title)"
          class="absolute top-2 right-2 bg-[#EAEAEA] text-black p-2 border-none cursor-pointer hover:opacity-80"
      >
        <svg
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 24 24"
            stroke-width="1.5"
            stroke="black"
            class="w-6 h-6 text-red-400 hover:text-red-800"
        >
          <path
              stroke-linecap="round"
              stroke-linejoin="round"
              d="m16.862 4.487 1.687-1.688a1.875 1.875 0 1 1 2.652 2.652L10.582 16.07a4.5 4.5 0 0 1-1.897 1.13L6 18l.8-2.685a4.5 4.5 0 0 1 1.13-1.897l8.932-8.931Zm0 0L19.5 7.125M18 14v4.75A2.25 2.25 0 0 1 15.75 21H5.25A2.25 2.25 0 0 1 3 18.75V8.25A2.25 2.25 0 0 1 5.25 6H10" />

          />
        </svg>
      </button>
    </div>
  </div>
</template>

<style scoped>
.group:hover .group-hover\:opacity-100 {
  opacity: 1;
}

.description {
  display: -webkit-box; /* Use the flexible box layout */
  -webkit-box-orient: vertical; /* Set vertical orientation */
  -webkit-line-clamp: 3; /* Limit the text to 3 lines */
  overflow: hidden;
  text-overflow: ellipsis; /* Add '...' at the end of the cut-off text */
  margin: 0;
}

</style>
