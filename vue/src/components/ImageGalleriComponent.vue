<script setup lang="ts">
import {defineComponent, nextTick, onMounted, ref} from 'vue'
import GalleryDescriptionComponent from "./GalleryDescriptionComponent.vue";
import ImageComponent from "./ImageComponent.vue";
import SmallArtWorkDetailsComponent from "./SmallArtWorkDetailsComponent.vue";
import HeaderComponent from "./HeaderComponent.vue";
import HeadingsComponent from "./HeadingsComponent.vue";
import LargeArtWorkDetailsComponent from "./LargeArtWorkDetailsComponent.vue";
import Masonry from 'masonry-layout'
import imagesLoaded from 'imagesloaded';

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
const selectedArtWork = ref<ArtWork | null>(null);

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

// Funktion til at håndtere klik på et kunstværk
const selectArtWork = (artWork: ArtWork) => {
  selectedArtWork.value = artWork;
};

</script>

<template>

  <div class="py-4 px-4 sm:px-20">
    <HeadingsComponent :level="1" text="GALLERI"></HeadingsComponent>

    <GalleryDescriptionComponent />


    <!-- Galleri grid -->
    <div class="masonry-grid">
      <div v-for="artWork in artWorks" :key="artWork.artWorkId" class="masonry-item">

        <!-- Billedkomponent -->
        <ImageComponent
            :artWork="artWork"
            @hover="hoveredArtWorks = $event"
            @click="selectArtWork(artWork)"
        />
        <!-- Detaljekomponent (vises kun ved hover) -->
        <SmallArtWorkDetailsComponent
            :artWork="hoveredArtWorks === artWork ? artWork : null"
            @click="selectArtWork(artWork)"
        />
        <!-- Pop-up komponent (vises kun ved klik på artWork) -->
        <LargeArtWorkDeatailsComponent
          v-if="selectedArtWork === artWork" :artWork="selectedArtWork"
        />
      </div>
    </div>
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