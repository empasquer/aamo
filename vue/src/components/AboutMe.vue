<script setup lang="ts">
import { ref, onMounted } from "vue";
import axios from "axios";
import BodyText from "./BodyTextComponent.vue";
import Headings from "./HeadingsComponent.vue";

// Reactive state
const description = ref("");

// Fetch the description when the component is mounted
onMounted(() => {
  axios
      .get("http://localhost:8080/api/about")
      .then((response) => {
        console.log("API Response:", response); // Check the entire response
        description.value = response.data.description;
      })
      .catch((error) => {
        console.error("Error fetching the description:", error);
        description.value = "Failed to load description.";
      });
});
</script>

<template>
  <div class="sm:p-4">
    <Headings :level="1" text="OM MIG" class="text-center"></Headings>

    <div class="flex flex-col sm:flex-row items-center sm:items-start gap-6">
      <!-- Image -->
      <img class="w-72 sm:w-96 p-4 mt-2" src="/eva.png" alt="Eva" />

      <!-- Description -->
      <BodyText :textContent="description" :width="2.5" sm:width="14" class="text-justify sm:leading-8"></BodyText>
    </div>
  </div>
</template>

<style scoped>
/* Your scoped styles */
</style>
