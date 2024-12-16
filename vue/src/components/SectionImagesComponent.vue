<script setup lang="ts">
import { onMounted, ref, nextTick } from "vue";
import HeadingsComponent from "./HeadingsComponent.vue";
import ImageLinkButtonComponent from "./ImageLinkButtonComponent.vue";
import ImageComponent from "./ImageComponent.vue";
import SmallArtWorkDetailsComponent from "./SmallArtWorkDetailsComponent.vue";

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

const chosenArtWorks = ref<ArtWork[]>([]);
const hoveredArtWork = ref<ArtWork | null>(null);

onMounted(async () => {
  try {
    const response = await fetch("http://localhost:8080/api/galleri/chosen-paintings");
    if (!response.ok) {
      throw new Error("Failed to fetch artworks");
    }

    const allArtWorks = (await response.json()) as ArtWork[];

    // Vi vil have de 4 nyeste billeder, det betyder at det er dem som har de største ID'er
    // sort by artWorkId (dsc == most recent first) +  take top 4
    chosenArtWorks.value = allArtWorks.sort((a, b) => b.artWorkId - a.artWorkId).slice(0, 4);

    await nextTick();
  } catch (error) {
    console.error("Error:", error);
  }
});
</script>

<template>
  <section class="mt-[15vh] md:mt-[20vh]">
    <HeadingsComponent class="text-left" :level="2" text="Mine malerier" />
    <div class="md:mx-[3rem] grid md:grid-cols-4 gap-2 grid-cols-1">
      <!-- Render backend images -->
      <div
        v-for="(artWork, index) in chosenArtWorks.slice(0, 3)"
        :key="artWork.artWorkId"
        class="mx-8 md:mx-0 h-[45vh] bg-cover bg-center"
        :style="{ backgroundImage: `url(${artWork.media.mediaUrl})` }"
      ></div>

      <!-- 4th image is a static button link -->
      <ImageLinkButtonComponent
        v-if="chosenArtWorks.length >= 4 && !loading"
        class="mx-8 md:mx-0 h-[45vh] bg-cover bg-center"
        href="/galleri"
        text="Se mere &rarr;"
        :image="chosenArtWorks[3].media.mediaUrl"
      ></ImageLinkButtonComponent>
    </div>
  </section>
</template>
