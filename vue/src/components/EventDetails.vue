<script lang="ts">
import Carousel from './Carousel.vue';
import axios from 'axios';
import Headings from './HeadingsComponent.vue'

export default {
  name: 'EventDetails',
  props: {
    eventId: {
      type: [String, Number],
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
        formatedStartDate: '',
        formatedEndDate: '',
        description: '',
      },
    };
  },
  components: {
    Carousel,
    Headings,
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
  <div class="flex flex-col items-center  gap-6 p-4 mt-32">

    <!-- Event Details -->
    <Headings :level=3 :text="event.title"></Headings>
    <h4>{{ event.formattedStartDate }} - {{ event.formattedEndDate }}</h4>
    <p class="sm:w-1/2 sm:w-full text-justify">{{ event.description }}</p>
    <!-- Carousel -->
    <Carousel v-if="mediaUrls && mediaUrls.length > 0" :media-urls="mediaUrls" />

  </div>
</template>

<style scoped>
/* Add any specific styles for the EventDetails component here */
</style>
