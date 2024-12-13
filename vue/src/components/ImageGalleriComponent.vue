<script setup lang="ts">
import {defineComponent, nextTick, onMounted, ref} from 'vue';
import GalleryDescriptionComponent from "./GalleryDescriptionComponent.vue";
import ImageWithHoverComponent from "./ImageWithHoverComponent.vue";
import HeaderComponent from "./HeaderComponent.vue";
import HeadingsComponent from "./HeadingsComponent.vue";
import ConfirmDeleteComponent from './ConfirmDeleteComponent.vue';

import Masonry from 'masonry-layout';
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

const artWorks = ref<ArtWork[]>([]);
const hoveredArtWorks = ref<ArtWork | null>(null);
let masonryInstance: Masonry | null = null;

const isLoggedIn = sessionStorage.getItem('loggedIn') === 'true';
const isConfirmDeleteVisible = ref(false);
const selectedArtWorkId = ref<number | null>(null);
const selectedArtWorkTitle = ref<string>("");
const route = useRoute();
const isAdminRoute = route.path.startsWith("/admin");

const openConfirmDelete = (artWorkId: number, title: string) => {
  selectedArtWorkId.value = artWorkId;
  selectedArtWorkTitle.value = title;
  isConfirmDeleteVisible.value = true;
};

const handleDeleteConfirmed = async (artWorkId: number) => {
  try {
    await axios.delete(`http://localhost:8080/api/artworks/${artWorkId}`);
    artWorks.value = artWorks.value.filter(artWork => artWork.artWorkId !== artWorkId);
    isConfirmDeleteVisible.value = false;
    if (masonryInstance) {
      await nextTick();
      masonryInstance.layout();
    }
  } catch (error) {
    console.error("Error deleting artwork:", error);
  }
};

const handleDeleteCancelled = () => {
  isConfirmDeleteVisible.value = false;
};

const initMasonry = () => {
  const grid = document.querySelector(".masonry-grid") as HTMLElement;
  if (grid) {
    imagesLoaded(grid, async () => {
      masonryInstance = new Masonry(grid, {
        itemSelector: ".masonry-item",
        columnWidth: ".masonry-item",
        percentPosition: true,
        gutter: 16,
      });

      masonryInstance.layout();
    });
  }
};

onMounted(async () => {
  try {
    const response = await fetch("http://localhost:8080/api/galleri");
    if (!response.ok) {
      throw new Error("Failed to fetch gallery data");
    }
    artWorks.value = await response.json();
    await nextTick();
    initMasonry();
  } catch (error) {
    console.error("Fejl ved hentning af artworks:", error);
  }
});
</script>

<template>
  <div class="py-4 px-4 sm:px-20">
    <HeadingsComponent :level="1" text="GALLERI"></HeadingsComponent>

    <GalleryDescriptionComponent />

    <div class="masonry-grid">
      <div v-for="artWork in artWorks" :key="artWork.artWorkId" class="masonry-item">
        <ImageWithHoverComponent
            :artWork="artWork"
            :isLoggedIn="isLoggedIn"
            @hover="hoveredArtWorks = $event"
            @deleteArtWork="openConfirmDelete"
        />
      </div>
    </div>

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
  display: grid;
}

.masonry-item {
  break-inside: avoid;
  width: calc((100% - 2 * 16px) / 3);
  margin-bottom: 16px;
}

@media (max-width: 768px) {
  .masonry-item {
    width: calc((100% - 16px) / 2);
  }
}

@media (max-width: 480px) {
  .masonry-item {
    width: 100%;
  }
}

.masonry-item img {
  display: block;
  width: 100%;
  height: auto;
}
</style>
