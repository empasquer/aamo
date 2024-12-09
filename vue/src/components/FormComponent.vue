<script setup lang="ts">
import { defineProps, computed } from 'vue';

// Define props for the component
const props = defineProps({
  title: {
    type: String,
    required: true,
  },
  formWidth: {
    type: String,
    default: 'w-48 md:w-64 lg:w-82',
  },
  action: {
    type: String,
    default: '',
  },
  method: {
    type: String,
    default: 'post',
  },
});

// Computed property for dynamic form width
const computedWidth = computed(() => {
  return props.formWidth;
});

// Method to handle form submission
const handleSubmit = (event: Event) => {
  event.preventDefault(); // Prevent default form submission
  const formElement = event.target as HTMLFormElement;
  formElement.submit(); // Manually submit the form
};
</script>

<template>
  <div class="flex flex-col items-center justify-center min-h-screen">
    <div :class="['p-8', 'shadow-md', computedWidth]" style="background-color: rgba(0, 0, 0, 0.5);">
      <h1 class="text-[#EAEAEA] text-2xl font-bold text-center mb-6">{{ title }}</h1>
      <form :action="action" :method="method" @submit="handleSubmit">
        <slot></slot>
      </form>
    </div>
  </div>
</template>

<style scoped>
/* You can add component-specific styles here */
</style>
