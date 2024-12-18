<script setup lang="ts">
import {defineComponent, nextTick, onMounted, onUnmounted, ref} from "vue";
import GalleryDescriptionComponent from "./GalleryDescriptionComponent.vue";
import ImageWithHoverComponent from "./ImageWithHoverComponent.vue";
import HeaderComponent from "./HeaderComponent.vue";
import HeadingsComponent from "./HeadingsComponent.vue";
import Masonry from "masonry-layout";
import imagesLoaded from "imagesloaded";
import ConfirmDeleteComponent from './ConfirmDeleteComponent.vue';
import LargeArtWorkComponent from "./LargeArtWorkComponent.vue";
import FilterSortMenuComponent from "./FilterSortMenuComponent.vue";
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
//const artWorks = ref([]);
const filteredArtWorks = ref<ArtWork[]>([]);
//const filteredArtWorks = ref([]);
const hoveredArtWorks = ref<ArtWork | null>(null);
let masonryInstance: Masonry | null = null;

const selectedArtWork = ref<ArtWork | null>(null);

const page = ref(0); // Start med første side
const pageSize = ref(10); // Antal billeder pr. side
const isFetching = ref(false); // For at forhindre dobbelt-kald
const totalPages = ref(0); // Total antal sider
const totalElements = ref(0);

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
  price: ""
});

// Hent billeder med pagination
const fetchArtWorks = async () => {
  try {
    if (isFetching.value) return;
    isFetching.value = true;

    const response = await axios.get("http://localhost:8080/api/galleri", {
      params: { page: page.value, size: pageSize.value },
    });

    const paginatedData = response.data;

    if (paginatedData.content && paginatedData.content.length > 0) {
      artWorks.value.push(...paginatedData.content);
      filteredArtWorks.value = [...artWorks.value];

      page.value += 1;
      totalPages.value = paginatedData.totalPages;
      totalElements.value = paginatedData.totalElements;

      await nextTick(); // Vent på, at DOM'en opdateres
      initMasonry();   // Initier eller opdater Masonry her
    }
  } catch (error) {
    console.error("Fejl ved hentning af artworks:", error);
  } finally {
    isFetching.value = false;
  }
};


// Scroll-event med debounce
let scrollTimeout: ReturnType<typeof setTimeout> | null = null;

const handleScroll = () => {
  // Hvis der allerede er en aktiv timeout, returner og gør ikke noget
  if (scrollTimeout) return;

  // Sætter en ny timeout
  scrollTimeout = setTimeout(() => {
    scrollTimeout = null;  // Nulstil timeout, når den er færdig

    // Hent de nødvendige værdier fra dokumentet for at kontrollere scroll-position
    const { scrollTop, scrollHeight, clientHeight } = document.documentElement;

    // Hvis scroll-positionen er tæt på bunden af dokumentet, hent næste side
    if (scrollTop + clientHeight >= scrollHeight - 200) {
      fetchArtWorks();  // Kalder funktionen til at hente flere data
    }
  }, 100);  // Debounce på 100ms for at undgå at kalde funktionen for ofte
};



// Funktion som filtrerer billederne
const filterArtWorks = () => {
  const { size, theme, color, sortOrder } = activeFilters.value;

  let filtered = [...artWorks.value];


  // Sortér efter pris
  if (sortOrder === "asc") {
    filtered.sort((a, b) => a.price - b.price);
  } else if (sortOrder === "desc") {
    filtered.sort((a, b) => b.price - a.price);
  } else {
  // Ingen sortering, behold rækkefølgen som den var
  filtered = [...artWorks.value];
  }

  // Filtrer på andre kriterier som størrelse, tema, farve
  filtered = filtered.filter((artWork) => {
    const matchesTheme =
        theme.length === 0 ||
        artWork.tags.some(
            (tag) => tag.tagType === "THEME" && theme.includes(tag.tagValue)
        );

    const matchesSize =
        size.length === 0 ||
        artWork.tags.some(
            (tag) => tag.tagType === "SIZE" && size.includes(tag.tagValue)
        );

    const matchesColor =
        color.length === 0 ||
        artWork.tags.some(
            (tag) => tag.tagType === "COLOR" && color.includes(tag.tagValue)
        );

    return matchesTheme && matchesSize && matchesColor;
  });

  filteredArtWorks.value = filtered;

  nextTick(() => initMasonry());
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
    filteredArtWorks.value = filteredArtWorks.value.filter(artWork => artWork.artWorkId !== artWorkId);
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

/*const fetchInitialArtWorks = async () => {
  try {
    const response = await fetch("http://localhost:8080/api/galleri");
    if (!response.ok) {
      throw new Error("Failed to fetch gallery data");
    }
    artWorks.value = await response.json();
    filteredArtWorks.value = [...artWorks.value];
    await nextTick(); // DOM er opdateret
    initMasonry(); // Initierer billeder
  } catch (error) {
    console.error("Fejl ved hentning af artworks:", error);
  }
};

 */

const setupScrollListener = () => {
  window.addEventListener("scroll", handleScroll);
  fetchArtWorks(); // Start første lazy-load batch
};


onMounted(() => {
  // Kald begge funktioner i rækkefølge
  fetchArtWorks(); // Din nuværende API-kald logik
  setupScrollListener(); // Scroll-event og lazy-loading
  initMasonry()
});

// fjerner scroll-event på unmount
onUnmounted(() => {
  window.removeEventListener("scroll", handleScroll);
});
</script>

<template>
  <div class="py-4 px-4 sm:px-20">
    <HeadingsComponent :level="1" text="GALLERI"></HeadingsComponent>

    <GalleryDescriptionComponent />


    <!-- Filter Menu -->
    <FilterSortMenuComponent
        :tags="artWorks.flatMap(artwork => artwork.tags)"
        :closeAll="closeAllFunction"
        @filter-applied="(filters) => {
    activeFilters.size = filters.size;
    activeFilters.theme = filters.theme;
    activeFilters.color = filters.color;
    activeFilters.sortOrder = filters.sortOrder;
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

    <div v-if="isFetching" class="loading-spinner"></div>

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
.loading-spinner {
  text-align: center;
  padding: 20px;
}

.loading-spinner::after {
  content: "";
  display: inline-block;
  width: 30px;
  height: 30px;
  border: 3px solid #999;
  border-top: 3px solid #000;
  border-radius: 50%;
  animation: spin 1s linear infinite;
}

@keyframes spin {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}
</style>
