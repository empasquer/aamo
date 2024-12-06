<script setup lang="ts">

// Define props for the component
const props = defineProps<{
  size?: string;
  textContent: string;
  width?: number
}>();
</script>

<template>
  <p :class="[
      'text-[#4A4A4A]',
      'font-inter',
      'p-4',
      size ? `text-${size}` : 'text-base sm:text-xl',
      width ? `w-${width}` : 'w-full'
    ]">
    {{ textContent }}
  </p>
</template>
