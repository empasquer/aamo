<script setup lang="ts">
import { computed } from "vue";

const props = defineProps<{
  textContent: string;
  size?: string;
  width?: number;
  color?: string;
}>();

const widthClass = computed(() => (props.width ? `w-${props.width}` : "w-full"));
const sizeClass = computed(() => (props.size ? `text-${props.size}` : "text-base sm:text-xl"));
const colorClass = computed(() => `text-${props.color || "[#4A4A4A]"}`);
</script>

<template>
  <p :class="`${colorClass} font-inter p-4 ${sizeClass} ${widthClass}`">
    {{ textContent }}
  </p>
</template>
