<script setup lang="ts">
import {defineComponent, onMounted, ref} from 'vue'

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
const artworks = ref<ArtWork[]>([]);
const isLoading = ref(true);


//Henter artwork nor componenten bliver brugt.
onMounted(async () =>{
  try {
    const response = await fetch("http://localhost:8080/artworks");
    if (!response.ok) {
      throw new Error("Failed to fetch artworks");
    }
    artworks.value = await response.json();
  } catch (error) {
    console.error(error);
  } finally {
    isLoading.value = false;
  }
});
</script>

<template>
  <div class="gallery-container">
    <h1>Gallery</h1>
    <p v-if="isLoading">Loading artworks...</p>
    <div v-else>
      <ImageComponent
          v-for="artwork in artworks"
          :key="artwork.artWorkId"
          :title="artwork.title"
          :description="artwork.description"
          :mediaUrl="artwork.media.mediaUrl"
          :price="artwork.price"
      />
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