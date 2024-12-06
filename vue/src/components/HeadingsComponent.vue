<script lang="ts">
import { defineComponent } from 'vue';
import { computed } from 'vue';
export default defineComponent ({
  name: "Headings",
  props: {
    text: {
      type: String,
      required: true,
    },
    level: {
      type: Number,
      default: 1
    }
  },
  setup(props) {
        const sizeClass = computed(() => {
      return {
        1: 'text-6xl sm:text-8xl',
        2: 'text-5xl sm:text-7xl',
        3: 'text-3xl sm:text-5xl',
      }[props.level]
    });
    return {
      sizeClass,
    };
  }
})
</script>

<template>
  <component :is="'h' + level" :class="` text-[#4A4A4A] ${sizeClass} font-inter p-4`">
    {{ text }}
  </component>
</template>

<style scoped>

</style>