<script setup lang="ts">
import { ref, watch, onMounted, computed } from "vue";
import axios from "axios";
import Carousel from "./Carousel.vue";
import Headings from "./HeadingsComponent.vue";
import BodyTextComponent from "./BodyTextComponent.vue";

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

const mediaUrls = computed(() => event.value.mediaUrls);

const fetchEvent = async (eventId: string | number) => {
  try {
    const response = await axios.get(`http://localhost:8080/api/event?eventId=${eventId}`);
    event.value = response.data;
  } catch (error) {
    console.error("Error fetching the event:", error);
  }
};

watch(
  () => props.eventId,
  (newId) => {
    fetchEvent(newId);
  }

);

onMounted(() => {
  updateScreenWidth();
  window.addEventListener("resize", updateScreenWidth);
  fetchEvent(props.eventId);
});
// Dynamically determine screen width
const isDesktop = ref(false);
const updateScreenWidth = () => {
  isDesktop.value = window.innerWidth >= 640; // Tailwind "sm" is typically >= 640px; use your breakpoint.
};
const dynamicColor = computed(() => (isDesktop.value ? "#4a4a4a" : "white"));
</script>

<template>
  <div class="flex flex-col items-center gap-6 p-4 ">
    <Headings :level="3" :text="event.title" :color=dynamicColor />

    <h4 class="font-inter text-white sm:text-neutral-600">
      {{ event.formattedStartDate }}
      <span class="text-white sm:text-neutral-600" v-if="event.formattedStartDate && event.formattedEndDate"> - </span>
      {{ event.formattedEndDate }}
    </h4>

    <Carousel v-if="mediaUrls && mediaUrls.length > 0" :media-urls="mediaUrls" />

    <BodyTextComponent  :color=dynamicColor :textContent="event.description" class="sm:w-3/4 w-full text-center sm:leading-8" />
  </div>


</template>

