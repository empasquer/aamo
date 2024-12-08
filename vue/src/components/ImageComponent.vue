<script setup lang="ts">

defineProps<{
  title: string;
  description: string;
  mediaUrl: string;
  price: number;
}>();

</script>

<template>
  <div class="image-container">
    <img :src="mediaUrl" :alt="title" class="artwork-image" />

    <h3>{{ title }}</h3>
    <p>{{ description }}</p>
    <p>Price: {{ price }} DKK</p>

  </div>

</template>

<style scoped>
.image-container {
  border: 1px solid #ccc;
  padding: 10px;
  margin: 10px;
  text-align: center;
  background-color: #f9f9f9;
}
.artwork-image {
  max-width: 100%;
  height: auto;
  border: 1px solid #ddd;
  margin-bottom: 10px;
}
</style>