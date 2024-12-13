<script setup lang="ts">
import { ref, watch, onMounted, computed } from "vue";
import axios from "axios";
import Carousel from "./Carousel.vue";
import Headings from "./HeadingsComponent.vue";
import BodyTextComponent from "./BodyTextComponent.vue";

// Props definition
const props = defineProps({
  eventId: {
    type: [String, Number],
    required: true,
  },
});

// Reactive state
const event = ref({
  mediaUrls: [],
  currentIndex: 0,
  title: "",
  startDate: "",
  endDate: "",
  formatedStartDate: "",
  formatedEndDate: "",
  description: "",
});

// Fetch event data
const fetchEvent = async (eventId: string | number) => {
  try {
    const response = await axios.get(`http://localhost:8080/api/event?eventId=${eventId}`);
    event.value = response.data;
  } catch (error) {
    console.error("Error fetching the event:", error);
  }
};

// Watch for changes to eventId prop
watch(
  () => props.eventId,
  (newId) => {
    fetchEvent(newId); // Re-fetch event data when eventId changes
  }
);

// Fetch event data when the component is mounted
onMounted(() => {
  fetchEvent(props.eventId);
});

// Computed property for formatted dates
const formattedStartDate = computed(() => {
  const date = new Date(event.value.startDate);
  return date.toLocaleDateString(); // Format as desired (MM/DD/YYYY or any format)
});

const formattedEndDate = computed(() => {
  if (event.value.endDate) {
    const date = new Date(event.value.endDate);
    return date.toLocaleDateString(); // Format as desired (MM/DD/YYYY or any format)
  }
  return ""; // If no end date, return an empty string
});

// Conditionally show both dates or just one
const dateRange = computed(() => {
  if (event.value.startDate && event.value.endDate) {
    return `${formattedStartDate.value} - ${formattedEndDate.value}`;
  } else {
    return formattedStartDate.value; // Only show start date if no end date
  }
});
</script>

<template>
  <div class="flex flex-col items-center gap-6 p-4 sm:mt-32">
    <!-- Event Details -->
    <Headings :level="3" :text="event.title"></Headings>

    <h4 class="font-inter text-[#4A4A4A]">{{ dateRange }}</h4>
    <!-- Display the date range or just the start date -->

    <!-- Only show Carousel if mediaUrls is not empty -->
    <Carousel v-if="event.mediaUrls && event.mediaUrls.length > 0" :media-urls="event.mediaUrls" />

    <!-- Display event description -->
    <BodyTextComponent :textContent="event.description" class="sm:w-3/4 w-full text-center sm:leading-8"></BodyTextComponent>
  </div>
</template>

<style scoped>
.carousel-button {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  background-color: rgba(0, 0, 0, 0.5);
  padding: 10px;
  border-radius: 50%;
  z-index: 10; /* Ensure the button is above other content */
  cursor: pointer;
}

.carousel-button-left {
  left: 10px;
}

.carousel-button-right {
  right: 10px;
}
</style>
