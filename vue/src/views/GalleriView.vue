<script setup lang="ts">
import { defineComponent, onMounted, ref } from "vue";
import ImageComponent from "../components/ImageComponent.vue";
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
const artworks = ref<ArtWork[]>([]);
const isLoading = ref(true);
const isLoggedIn = ref(false);

onMounted(() => {
  // Check if user is logged in by checking sessionStorage
  const status = sessionStorage.getItem('loggedIn');
  isLoggedIn.value = status === 'true';
});

//Henter artwork nor componenten bliver brugt.
onMounted(async () => {
  try {
    const response = await fetch("http://localhost:8080/api/galleri");
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
    <HeadingsComponent :level=1 text="GALLERI" class="text-center"></HeadingsComponent>
    <p v-if="isLoggedIn">You are logged in!</p>
    <p v-if="isLoading">Henter kunstværker.. :)</p>
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
