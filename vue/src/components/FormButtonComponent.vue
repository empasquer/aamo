<script setup lang="ts">
import { defineProps } from 'vue';

// Define props for the component
const props = defineProps({
  type: {
    type: String,
    default: 'submit',
  },
  loading: {
    type: Boolean,
    default: false,
  },
});
</script>

<template>
  <button
      :type="props.type"
      :disabled="props.loading"
      class="py-2.5 px-6 bg-black bg-opacity-50 text-[#EAEAEA] rounded-none"

  >
    <slot v-if="!props.loading"></slot>
    <span v-else class="text-[#EAEAEA]">Indlæser...</span>
  </button>
</template>

<style scoped>
button:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

</style>
