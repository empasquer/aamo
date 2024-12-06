<script lang="ts">
import axios from 'axios';
// comment to push
export default {
  name: 'AboutMeView',
  data() {
    return {
      description: '',
    };
  },
  mounted() {
    axios
        .get('http://localhost:8080/api/about')
        .then((response) => {
          console.log('API Response:', response); // Check the entire response
          this.description = response.data.description;
        })
        .catch((error) => {
          console.error('Error fetching the description:', error);
          this.description = 'Failed to load description.';
        });
  },
};
</script>

<template>
  <div class="p-4">
    <h1 class="text-center">OM MIG</h1>

    <div class="flex flex-col sm:flex-row items-center sm:items-start gap-6">
      <!-- Image -->
      <img
          class="w-48 md:w-64 lg:w-82"
          src="../assets/eva.png"
          alt="Eva"
      />

      <!-- Description -->
      <p class="text-base sm:text-lg leading-relaxed text-gray-700">
        {{ description }}
      </p>
    </div>
  </div>
</template>

<style scoped>
/* Your scoped styles */
</style>
