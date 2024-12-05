<script lang="ts">
import AboutMe from '../components/AboutMe.vue';
import EventTimeline from '../components/EventTimeline.vue';

export default {
  name: 'AboutMeView',
  components: {
    AboutMe,
    EventTimeline,
  },
};
</script>

<template>
  <div>
    <AboutMe></AboutMe>
   <EventTimeline></EventTimeline>
  </div>
</template>

<style scoped>
/* Your scoped styles */
</style>
