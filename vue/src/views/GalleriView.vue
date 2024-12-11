<script setup lang="ts">
import {defineComponent, onMounted, ref} from 'vue'
import GalleryDescriptionComponent from "../components/GalleryDescriptionComponent.vue";
import ImageComponent from "../components/ImageComponent.vue";
import SmallArtWorkDetailsComponent from "../components/SmallArtWorkDetailsComponent.vue";
import HeadingsComponent from "../components/HeadingsComponent.vue";

//definerer artwork interfacet.
interface ArtWork {
  artWorkId: number;
  title: string;
  description: string;
  price: number;
  media: {
    mediaUrl: string;
  };
}

//her er de reaktive variabler
const artWorks = ref<ArtWork[]>([]);
const hoveredArtWorks = ref<ArtWork | null>(null);


//Henter artwork nor componenten bliver brugt.
onMounted(async () => {
  try {
    const response = await fetch("http://localhost:8080/api/galleri");
    if (!response.ok) {
      throw new Error("Failed to fetch gallery data");
    }
    artWorks.value = await response.json();
  } catch (error) {
    console.error("Fejl ved hentniong af artworks:", error);
  }
});
</script>

<template>

  <HeadingsComponent :level="1" text="GALLERI"></HeadingsComponent>

  <div class="p-4">
    <GalleryDescriptionComponent />



    <!-- Galleri -->
    <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-4">
      <div v-for="artWork in artWorks" :key="artWork.artWorkId" class="relative">
        <!-- Billedkomponent -->
        <ImageComponent
            :artWork="artWork"
           @hover="hoveredArtWorks = $event"
        />
        <!-- Detaljekomponent (vises kun ved hover) -->
        <SmallArtWorkDetailsComponent
            :artWork="hoveredArtWorks === artWork ? artWork : null"
        />


      </div>
    </div>
  </div>
</template>

<style scoped>
.gallery-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: 20px;
}
</style>