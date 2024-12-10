<script setup lang="ts">
import { computed, ref, watch, onMounted } from 'vue';
import axios from 'axios';
import Carousel from './Carousel.vue';
import Headings from './HeadingsComponent.vue';
import BodyText from './BodyTextComponent.vue';

// Define the eventId prop
const props = defineProps({
  eventId: {
    type: [String, Number],
    required: true,
  },
});

// Ref to hold event data
const event = ref({
  mediaUrls: [],
  currentIndex: 0,
  title: '',
  startDate: '',
  endDate: '',
  formatedStartDate: '',
  formatedEndDate: '',
  description: '',
});

// Method to fetch event data
const fetchEvent = async (eventId: string | number) => {
  if (!eventId) {
    console.error('Invalid eventId:', eventId);
    return; // Stop if eventId is invalid
  }
  try {
    const response = await axios.get(`http://localhost:8080/api/event?eventId=${eventId}`);
    event.value = response.data;
  } catch (error) {
    console.error('Error fetching the event:', error);
  }
};

// Watch for changes to eventId and re-fetch event data
watch(
    () => props.eventId,
    (newId) => {
      if (newId) {
        fetchEvent(newId);
      }
    },
    { immediate: true } // Ensure this runs immediately when the component is mounted
);

// Fetch event data when component is mounted
onMounted(() => {
  if (props.eventId) {
    fetchEvent(props.eventId);
  }
});

// Expose mediaUrls computed property
const mediaUrls = computed(() => event.value.mediaUrls);
</script>

<template>
  <div class="flex flex-col items-center gap-6 p-4 sm:mt-32">
    <!-- Event Details -->
    <Headings :level="3" :text="event.title"></Headings>

    <h4 class="font-inter text-[#4A4A4A]">{{ event.formatedStartDate }} - {{ event.formatedEndDate }}</h4>
    <!-- Carousel -->
    <Carousel v-if="mediaUrls && mediaUrls.length > 0" :media-urls="mediaUrls" />
    <BodyText :textContent="event.description" class="sm:w-3/4 w-full text-center sm:leading-8"></BodyText>
  </div>
</template>

<style scoped>
/* Add any specific styles for the EventDetails component here */
</style>
