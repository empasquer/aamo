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
      class="py-2.5 px-6 bg-black bg-opacity-50 text-[#EAEAEA] rounded-none"

  >
    <slot></slot>
  </button>
</template>

<style scoped>

</style>
