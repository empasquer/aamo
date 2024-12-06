<script setup lang="ts">

// Define props for the component
const props = defineProps<{
  size?: string;
  width?: string; // doesn't work
}>();
</script>

<template>
  <p :class="[
      'text-[#4A4A4A]',
      'font-inter',
      'p-4',
      size ? `text-${size}` : 'text-base sm:text-xl',
      width ? `font-${width}` : 'font-normal'
    ]">
    <slot />
  </p>
</template>
<style scoped>

</style>