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
  fetchEvent(props.eventId);
});
</script>

<template>
  <div class="flex flex-col items-center gap-6 p-4 sm:mt-32">
    <Headings :level="3" :text="event.title" color="white" />

    <h4 class="font-inter text-white">
      {{ event.formattedStartDate }}
      <span class="text-white" v-if="event.formattedStartDate && event.formattedEndDate"> - </span>
      {{ event.formattedEndDate }}
    </h4>

    <Carousel v-if="mediaUrls && mediaUrls.length > 0" :media-urls="mediaUrls" />

    <BodyTextComponent color="white" :textContent="event.description" class="sm:w-3/4 w-full text-center sm:leading-8" />
  </div>


</template>

