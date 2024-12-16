<script setup lang="ts">
import {  computed } from 'vue';

// Define props for the component
const props = defineProps({
  title: {
    type: String,
    required: true,
  },
  formWidth: {
    type: String,
    default: 'w-60 md:w-80 lg:w-86',
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


const emit = defineEmits(['submit']);

const handleFormSubmit = (event: Event) => {
  // Prevent default form submission behavior
  event.preventDefault();

  // Emit the 'submit' event if no action prop is set
  if (!props.action) {
    emit('submit', event);
  }
};
</script>

<template>
  <div class="flex flex-col items-center justify-center ">
    <div :class="['p-8', 'shadow-md', computedWidth,  'mx-auto']" style="; background-color: rgba(0, 0, 0, 0.5);">
      <h1 class="text-[#EAEAEA] text-4xl font-bold text-center mb-6">{{ title }}</h1>
      <form :action="action" :method="method" @submit="handleFormSubmit">
      <slot></slot>
      </form>
    </div>
  </div>
</template>

<style scoped>
/* You can add component-specific styles here */
</style>
