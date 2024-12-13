<script setup lang="ts">
import {defineComponent, nextTick, onMounted, ref} from 'vue'
import GalleryDescriptionComponent from "./GalleryDescriptionComponent.vue";
import ImageComponent from "./ImageComponent.vue";
import SmallArtWorkDetailsComponent from "./SmallArtWorkDetailsComponent.vue";
import HeaderComponent from "./HeaderComponent.vue";
import HeadingsComponent from "./HeadingsComponent.vue";
import ConfirmDeleteComponent from './ConfirmDeleteComponent.vue';

import Masonry from 'masonry-layout'
import imagesLoaded from 'imagesloaded';
import axios from "axios";
import {useRoute} from "vue-router";

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

interface ArtWorkTag {
  tagType: string;
  tagValue: string;
}

//her er de reaktive variabler
const artWorks = ref<ArtWork[]>([]);
const hoveredArtWorks = ref<ArtWork | null>(null);
//const masonry = new Masonry(artWorks);

//For deleting
const isLoggedIn = sessionStorage.getItem('loggedIn') === 'true';
const isConfirmDeleteVisible = ref(false);
const selectedArtWorkId = ref<number | null>(null);
const selectedArtWorkTitle = ref<string>("");

console.log('isLoggedIn:', isLoggedIn);

const route = useRoute();
const isAdminRoute = route.path.startsWith("/admin");


// Open confirmation dialog
const openConfirmDelete = (artWorkId: number, title: string) => {
  console.log('Delete clicked for:', artWorkId, title);  // Debugging log

  selectedArtWorkId.value = artWorkId;
  selectedArtWorkTitle.value = title;
  isConfirmDeleteVisible.value = true;
};

// Handle confirmed deletion
const handleDeleteConfirmed = async (artWorkId: number) => {
  try {
    // Make API call to delete the artwork
    await axios.delete(`/api/artworks/${artWorkId}`);

    // Remove the artwork from the list
    artWorks.value = artWorks.value.filter(artWork => artWork.artWorkId !== artWorkId);

    // Close the confirmation dialog
    isConfirmDeleteVisible.value = false;
  } catch (error) {
    console.error("Error deleting artwork:", error);
  }
};

// Handle cancelled deletion
const handleDeleteCancelled = () => {
  isConfirmDeleteVisible.value = false;
};

const initMasonry = () => {
  const grid = document.querySelector(".masonry-grid") as HTMLElement;
  if (grid) {
    imagesLoaded(grid, () => {
      const masonry = new Masonry(grid, {
        itemSelector: ".masonry-item",
        columnWidth: ".masonry-item",
        percentPosition: true,
        gutter: 16,
      });
      masonry.layout()
    });
  }
};


//Henter artwork nor componenten bliver brugt.
onMounted(async () => {
  try {
    const response = await fetch("http://localhost:8080/api/galleri");
    if (!response.ok) {
      throw new Error("Failed to fetch gallery data");
    }
    artWorks.value = await response.json();
    await nextTick(); // venter til at DOM er opdaterede.
    initMasonry(); //nu initialiserer den billederne ved hjælp af masonry
  } catch (error) {
    console.error("Fejl ved hentniong af artworks:", error);
  }
});


</script>

<template>

  <div class="py-4 px-4 sm:px-20">
    <HeadingsComponent :level="1" text="GALLERI"></HeadingsComponent>

    <GalleryDescriptionComponent />


    <!-- Galleri grid -->
    <!-- <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-4"> -->
    <!-- Masonry grid -->
    <div class="masonry-grid">
      <div v-for="artWork in artWorks" :key="artWork.artWorkId" class="masonry-item">
        <!-- Billedkomponent -->
        <ImageComponent
            :artWork="artWork"
            :isLoggedIn="isLoggedIn"
            @hover="hoveredArtWorks = $event"
        />
        <!-- Detaljekomponent (vises kun ved hover) -->
        <SmallArtWorkDetailsComponent
            :artWork="hoveredArtWorks === artWork ? artWork : null"
            :isLoggedIn="isLoggedIn"
        />
      </div>

      <!-- Trashcan button, only visible if logged in and URL starts with /admin -->
      <button
          v-if="isLoggedIn && isAdminRoute"
          @click="openConfirmDelete"
          class="absolute bottom-2 right-2 bg-red-700 text-white p-2 border-none cursor-pointer hover:bg-red-800"
      >
        <!-- Trashcan Icon (SVG) -->
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
    </div>

    <!-- Confirm Delete Modal -->
    <ConfirmDeleteComponent
        v-if="isConfirmDeleteVisible"
        :toDelete="{ toDeleteId: selectedArtWorkId, title: selectedArtWorkTitle }"
        @close="handleDeleteCancelled"
        @confirm-delete="handleDeleteConfirmed"
    />
  </div>
</template>

<style scoped>
.masonry-grid {
  display: grid; /* Masonry håndterer layout*/
}


.masonry-item {
  break-inside: avoid; /* Forhindrer sammenbrud mellem elementer*/
  width: calc((100% - 2 * 16px) / 3); /* 3 kolonner med 16px gutter */
  margin-bottom: 16px;
}

@media (max-width: 768px) {
  .masonry-item {
    width: calc((100% - 16px) / 2); /* 2 kolonner på mindre skærme */
  }
}

@media (max-width: 480px) {
  .masonry-item {
    width: 100%; /* Ét billede pr. række */
  }
}

.masonry-item img {
  display: block;
  width: 100%;
  height: auto;
}
</style>