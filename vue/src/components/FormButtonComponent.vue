<script setup lang="ts">
import { defineProps } from 'vue';

// Define props for the component
const props = defineProps({
  type: {
    type: String,
    default: 'submit',
  },
});
</script>

<template>
  <button
      :type="props.type"
      class="w-full p-3 bg-black bg-opacity-50 text-[#EAEAEA] hover:bg-gray-700 focus:outline-none focus:ring-2 focus:ring-gray-400 rounded-none"
  >
    <slot></slot>
  </button>
</template>

<style scoped>
/* You can add component-specific styles here */
</style>
