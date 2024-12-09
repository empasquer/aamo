<script setup lang="ts">
import { defineProps, ref } from 'vue';

// Define the props for the component
const props = defineProps({
  label: {
    type: String,
    required: true,
  },
  value: {
    type: String,
  },
  type: {
    type: String,
    default: 'text',
  },
  placeholder: {
    type: String,
    default: '',
  },
  name: {
    type: String,
    default: '',
  },
});
</script>

<template>
  <div class="mb-4">
    <label :for="props.label" class="text-[#EAEAEA] block text-sm font-medium mb-1">{{ props.label }}</label>
    <input
        :type="props.type"
        :id="props.label"
        v-model="props.value"
        :placeholder="props.placeholder"
        :name="props.name"
        class="w-full p-2 border focus:outline-none focus:ring-2 focus:ring-gray-400"
    />
  </div>
</template>

<style scoped>
/* You can add component-specific styles here */
</style>
