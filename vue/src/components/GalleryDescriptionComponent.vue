<script setup lang="ts">
import axios from 'axios';
import {onMounted, ref} from "vue";

const galleryDescription = ref<string>('');

onMounted(async () => {
  try {
    const descriptionResponse = await fetch('/galleryDescription.json');
    if (!descriptionResponse.ok) throw new Error('Failed to fetch Gallery Description');
    const descriptionData = await descriptionResponse.json();
    galleryDescription.value = descriptionData.description;
  } catch (error) {
    console.error('Fejl ved hentning af json fil: ', error);
  }
});

</script>

<template>
  <div class="p-4">
    <p class="text-base sm:text-lg leading-relaxed text-gray-700">
      {{ galleryDescription }}
    </p>
  </div>
</template>

<style scoped>
</style>
