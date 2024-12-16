<script lang="ts">
import { defineComponent } from "vue";
import Timeline from "./SimpleTimeline.vue";

export default defineComponent({
  name: "App",
  components: {
    Timeline,
  },
});
</script>

<template>
  <div id="app" class="bg-gray-100">
    <h1 class="text-3xl font-bold text-center p-6">Event Timeline</h1>
    <Timeline />
  </div>
</template>

<style scoped>
/* Global styles */
</style>
