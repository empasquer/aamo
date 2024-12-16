<script setup lang="ts">
import {defineComponent, nextTick, onMounted, ref} from 'vue';
import GalleryDescriptionComponent from "./GalleryDescriptionComponent.vue";
import ImageWithHoverComponent from "./ImageWithHoverComponent.vue";
import HeaderComponent from "./HeaderComponent.vue";
import HeadingsComponent from "./HeadingsComponent.vue";
import ConfirmDeleteComponent from './ConfirmDeleteComponent.vue';
import LargeArtWorkComponent from "./LargeArtWorkComponent.vue";
import FilterSortMenuComponent from "./FilterSortMenuComponent.vue";


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
const filteredArtWorks = ref<ArtWork[]>([]);
const hoveredArtWorks = ref<ArtWork | null>(null);
let masonryInstance: Masonry | null = null;

const selectedArtWork = ref<ArtWork | null>(null);

const isLoggedIn = sessionStorage.getItem('loggedIn') === 'true';
const isConfirmDeleteVisible = ref(false);
const selectedArtWorkId = ref<number | null>(null);
const selectedArtWorkTitle = ref<string>("");
const route = useRoute();
const isAdminRoute = route.path.startsWith("/admin");

//Filter instillinger
const activeFilters = ref({
  size: [],
  theme: [],
  color: [],
});

// Funktion som filtrerer billederne
const filterArtWorks = () => {
  // Hvis ingen filtre er valgt, vis alle malerier
  if (
      activeFilters.value.size.length === 0 &&
      activeFilters.value.color.length === 0 &&
      activeFilters.value.theme.length === 0
  ) {
    filteredArtWorks.value = [...artWorks.value];
  } else {
    filteredArtWorks.value = artWorks.value.filter((artWork) => {
      // Tjek for tema
      const matchesTheme = activeFilters.value.theme.length === 0 || artWork.tags.some(
          (tag) => tag.tagType === "THEME" && activeFilters.value.theme.includes(tag.tagValue)
      );

      // Tjek for størrelse
      const matchesSize = activeFilters.value.size.length === 0 || artWork.tags.some(
          (tag) => tag.tagType === "SIZE" && activeFilters.value.size.includes(tag.tagValue)
      );

      // Tjek for farve
      const matchesColor = activeFilters.value.color.length === 0 || artWork.tags.some(
          (tag) => tag.tagType === "COLOR" && activeFilters.value.color.includes(tag.tagValue)
      );

      // Hvis kun tema er valgt, vis billeder der matcher temaet uanset størrelse og farve
      if (activeFilters.value.size.length === 0 && activeFilters.value.color.length === 0 && activeFilters.value.theme.length > 0) {
        return matchesTheme;
      }

      // Hvis kun størrelse er valgt, vis billeder der matcher størrelsen uanset farve og tema
      if (activeFilters.value.size.length > 0 && activeFilters.value.color.length === 0 && activeFilters.value.theme.length === 0) {
        return matchesSize;
      }

      // Hvis kun farve er valgt, vis billeder der matcher farven uanset størrelse og tema
      if (activeFilters.value.color.length > 0 && activeFilters.value.size.length === 0 && activeFilters.value.theme.length === 0) {
        return matchesColor;
      }

      // Hvis tema og størrelse er valgt, vis billeder der matcher begge uanset farve
      if (activeFilters.value.theme.length > 0 && activeFilters.value.size.length > 0 && activeFilters.value.color.length === 0) {
        return matchesTheme && matchesSize;
      }

      // Hvis tema og farve er valgt, vis billeder der matcher begge uanset størrelse
      if (activeFilters.value.theme.length > 0 && activeFilters.value.color.length > 0 && activeFilters.value.size.length === 0) {
        return matchesTheme && matchesColor;
      }

      // Hvis størrelse og farve er valgt, vis billeder der matcher begge uanset tema
      if (activeFilters.value.size.length > 0 && activeFilters.value.color.length > 0 && activeFilters.value.theme.length === 0) {
        return matchesSize && matchesColor;
      }

      // Hvis tema, størrelse og farve er valgt, vis billeder der matcher alle tre
      return matchesTheme && matchesSize && matchesColor;
    });
  }

  nextTick(() => initMasonry()); // Opdater Masonry efter ændring
};



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

const openArtWorkModal = (artWork: ArtWork) => {
  selectedArtWork.value = artWork;
};

const closeArtWork = () => {
  selectedArtWork.value = null;
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
    const response = await fetch("http://localhost:8080/api/other-works");
    if (!response.ok) {
      throw new Error("Failed to fetch gallery data");
    }
    artWorks.value = await response.json();
    filteredArtWorks.value = [...artWorks.value];
    await nextTick(); // venter til at DOM er opdaterede. Denne brokker sig som om await ikke er foran, hvilket det er. sååå bare endnu en dum fejl, som vi bliver nødt til at ignorere.
    initMasonry(); //nu initialiserer den billederne ved hjælp af masonry
  } catch (error) {
    console.error("Fejl ved hentning af artworks:", error);
  }
});
</script>

<template>
  <div class="py-4 px-4 sm:px-20">
    <HeadingsComponent :level="1" text="ØVRIGE VÆRKER"></HeadingsComponent>

    <!-- Filter Menu -->
    <FilterSortMenuComponent
        :tags="artWorks.flatMap(artwork => artwork.tags)"
        @filter-applied="(filters) => {
          activeFilters.size = filters.size;
          activeFilters.theme = filters.theme;
          activeFilters.color = filters.color;
          filterArtWorks();
        }"
    />


    <!-- Galleri grid -->
    <div class="masonry-grid">
      <div v-for="artWork in filteredArtWorks" :key="artWork.artWorkId" class="masonry-item">

        <!-- Billedkomponent -->
        <ImageWithHoverComponent
            :artWork="artWork"
            :isLoggedIn="isLoggedIn"
            @hover="hoveredArtWorks = $event"
            @deleteArtWork="openConfirmDelete"
            @openModal="openArtWorkModal(artWork)"
        />
      </div>
    </div>

    <LargeArtWorkComponent
        v-if="selectedArtWork"
        :artWork="selectedArtWork"
        @close="closeArtWork"
    />

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
