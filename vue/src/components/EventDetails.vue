<script lang="ts">
import Carousel from './Carousel.vue';
import axios from 'axios';

export default {
  name: 'EventDetails',
  props: {
    eventId: {
      type: [String, Number], // Assuming eventId can be a string or number
      required: true,
    },
  },
  data() {
    return {
      event: {
        mediaUrls: [],
        currentIndex: 0,
        title: '',
        startDate: '',
        endDate: '',
        description: '',
      },
    };
  },
  components: {
    Carousel,
  },
  computed: {
    mediaUrls() {
      return this.event.mediaUrls;
    },
  },
  methods: {
    fetchEvent(eventId: string | number) {
      axios
          .get(`http://localhost:8080/api/event?eventId=${eventId}`)
          .then((response) => {
            this.event = response.data;
          })
          .catch((error) => {
            console.error('Error fetching the event:', error);
          });
    },
  },
  watch: {
    eventId(newId) {
      this.fetchEvent(newId); // Re-fetch the event when eventId changes
    },
  },
  mounted() {
    this.fetchEvent(this.eventId); // Fetch event data when mounted
  },
};
</script>

<template>
  <div class="flex flex-col items-center sm:items-start gap-6 p-40">
    <!-- Carousel -->
    <Carousel v-if="mediaUrls && mediaUrls.length > 0" :media-urls="mediaUrls" />

    <!-- Event Details -->
    <p>{{ event.title }}</p>
    <p>{{ event.startDate }} - {{ event.endDate }}</p>
    <p>{{ event.description }}</p>
  </div>
</template>

<style scoped>
/* Add any specific styles for the EventDetails component here */
</style>
