<script setup lang="ts">
import { ref, watch, onMounted } from "vue";
import axios from "axios";
import Carousel from "./Carousel.vue";
import Headings from "./HeadingsComponent.vue";
import BodyTextComponent from "./BodyTextComponent.vue";

// Props
const props = defineProps<{
  eventId: string | number;
}>();

// Reactive state
const event = ref({
  mediaUrls: [] as string[],
  currentIndex: 0,
  title: "",
  startDate: "",
  endDate: "",
  formattedStartDate: "",
  formattedEndDate: "",
  description: "",
});

// Computed property for mediaUrls
const mediaUrls = () => event.value.mediaUrls;

// Method to fetch event data
const fetchEvent = async (eventId: string | number) => {
  try {
    const response = await axios.get(`http://localhost:8080/api/event?eventId=${eventId}`);
    event.value = response.data;
  } catch (error) {
    console.error("Error fetching the event:", error);
  }
};

// Watch for changes in `eventId` prop and fetch event data
watch(
  () => props.eventId,
  (newId) => {
    fetchEvent(newId);
  }
);

// Fetch event data when the component is mounted
onMounted(() => {
  fetchEvent(props.eventId);
});
</script>

<template>
  <div class="flex flex-col items-center gap-6 p-4 sm:mt-32">
    <!-- Event Details -->
    <Headings :level="3" :text="event.title" />

    <h4 class="font-inter text-[#4A4A4A]">{{ event.formattedStartDate }} - {{ event.formattedEndDate }}</h4>

    <!-- Carousel -->
    <Carousel v-if="mediaUrls() && mediaUrls().length > 0" :media-urls="mediaUrls()" />

    <BodyTextComponent :text-content="event.description" class="sm:w-3/4 w-full text-center sm:leading-8" />
  </div>
</template>
