<script setup lang="ts">
import { computed } from "vue";

// Props definition
const props = defineProps<{
  text: string;
  level?: number;
  color?: string;
}>();

// Computed classes
const sizeClass = computed(() => {
  return {
    1: "text-6xl sm:text-8xl",
    2: "text-5xl sm:text-7xl",
    3: "text-3xl sm:text-5xl",
  }[props.level || 1];
});
const colorClass = computed(() => `text-${props.color || "[#4A4A4A]"}`);
</script>

<template>
  <component :is="'h' + (props.level || 1)" :class="`${colorClass} ${sizeClass} font-inter p-4`">
    {{ props.text }}
  </component>
</template>

<style scoped></style>
