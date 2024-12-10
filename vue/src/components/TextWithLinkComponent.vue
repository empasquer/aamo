<script setup lang="ts">
import { defineProps, computed } from 'vue';

// Define props for the component
const props = defineProps({
  text: {
    type: String,
    required: true,
  },
  fontSize: {
    type: String,
    default: 'text-base',
  },
  containerHeight: {
    type: String,
    default: 'h-auto',
  },
  containerWidth: {
    type: String,
    default: 'w-full',
  },
  textAlign: {
    type: String,
    default: 'text-left',
  },
  padding: {
    type: String,
    default: 'p-0',
  },
  fontWeight: {
    type: String,
    default: 'font-normal',
  },
  link: {
    type: String,
    default: '#', // Default to "#" if no link is provided
  },
});

// Computed property for dynamic classes
const computedClasses = computed(() => {
  return [
    'text-[#EAEAEA]',
    'font-inter',
    props.fontSize,
    props.containerHeight,
    props.containerWidth,
    props.textAlign,
    props.padding,
    props.fontWeight,
  ].join(' ');
});

// Computed property to split text into parts
const textParts = computed(() => {
  const parts = props.text.split('Klik her');
  return {
    before: parts[0], // Text before "Klik her"
    clickable: 'Klik her', // The clickable part
    after: parts[1] || '', // Text after "Klik her"
  };
});

// Method to handle click event
const handleClick = () => {
  if (props.link && props.link !== '#') {
    window.location.href = props.link; // Redirect to the link
  } else {
    console.log('No valid link specified.');
  }
};
</script>

<template>
  <div :class="computedClasses">
    <span>{{ textParts.before }}</span>
    <a href="#" @click.prevent="handleClick" class="underline cursor-pointer text-[#EAEAEA] font-inter">
      {{ textParts.clickable }}
    </a>
    <span>{{ textParts.after }}</span>
  </div>
</template>

<style scoped>
a:hover {
  color: inherit; /* Keeps the same color on hover */
}
</style>
