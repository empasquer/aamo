<script setup lang="ts">
import { ref, onMounted } from "vue";
import axios from "axios";

const description = ref<string>("");

// TODO: der skal helt sikkert ændres i api/about endpoints --- ikke testet

onMounted(() => {
  axios
    .get("/api/about")
    .then((response) => {
      description.value = response.data.description;
    })
    .catch((error) => {
      console.error("Error fetching the description:", error);
      description.value = "Failed to load description.";
    });
});
</script>

<template>
  <div class="p-4">
    <h1 class="text-center">OM MIG</h1>

    <div class="flex flex-col sm:flex-row items-center sm:items-start gap-6">
      <img class="w-48 md:w-64 lg:w-82" src="../assets/eva.png" alt="Eva" />

      <p class="text-base sm:text-lg leading-relaxed text-gray-700">
        {{ description }}
      </p>
    </div>
  </div>
</template>

<style scoped></style>
