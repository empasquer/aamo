<script setup lang="ts">
import {defineComponent, onMounted, ref} from 'vue'
import ImageComponent from "../components/ImageComponent.vue";
import SmallArtWorkDetailsComponent from "../components/SmallArtWorkDetailsComponent.vue";

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
onMounted(async () =>{
  try {
    const response = await fetch("http://localhost:8080/api/galleri");
    if (!response.ok) {
      throw new Error("Failed to fetch artworks");
    }
    artWorks.value = await response.json();
  } catch (error) {
    console.error("Fejl ved hentniong af artworks:", error);
  }
});
</script>

<template>
  <div class="grid grid-cols-3 gap-4">
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
</template>

<style scoped>

</style>