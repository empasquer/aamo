<script lang="ts" setup>
import { ref, onMounted, watch, onUnmounted } from "vue";
import EventDetails from "./EventDetails.vue";
import EventButton from "./EventButton.vue";
import Headings from "./HeadingsComponent.vue";
import axios from "axios";

const events = ref([]);
const selectedEvent = ref(null); // Use an object instead of just eventId
const isMobile = ref(window.innerWidth <= 768);
const loading = ref(true);

function selectEvent(eventId: number) {
  const event = events.value.find((e) => e.eventId === eventId); // Find the full event object
  if (event) {
    selectedEvent.value = event; // Set the full event object
  }
}

function displayFirstEvent() {
  if (events.value.length > 0) {
    selectedEvent.value = events.value[0]; // Set the first event object
  }
}

function handleResize() {
  isMobile.value = window.innerWidth <= 768;
}

onMounted(async () => {
  try {
    const response = await axios.get("http://localhost:8080/api/events");
    events.value = response.data;
    displayFirstEvent();
  } catch (error) {
    console.error("Error fetching events:", error);
  } finally {
    loading.value = false;
  }
  window.addEventListener("resize", handleResize);
});

onUnmounted(() => {
  window.removeEventListener("resize", handleResize);
});

watch(
  () => isMobile.value,
  (value) => {
    if (!value) {
      displayFirstEvent();
    }
  }
);
</script>

<template>
  <div id="events" class="events-container flex flex-col">
    <!-- Heading -->
    <Headings text="EVENTS" :level="2" class="flex p-6" />

    <!-- Loading Spinner or Placeholder -->
    <div v-if="loading" class="flex justify-center items-center w-full h-[400px]">
      <p>Loading events...</p>
      <!-- You can add a spinner here if needed -->
    </div>

    <!-- Timeline & Event Details -->
    <div v-else class="timeline flex flex-col w-full sm:flex-row sm:p-4">
      <!-- Timeline Section -->
      <div class="flex flex-col items-center w-full sm:w-1/2 pt-4">
        <i class="fa-solid fa-chevron-up text-gray-600 text-7xl"></i>
        <div class="w-2 bg-gray-600 -mt-12 h-16"></div>

        <!-- Event List -->
        <div class="overflow-y-auto w-full flex justify-center h-4/6">
          <ul class="space-y-8">
            <li v-for="event in events" :key="event.eventId" class="relative flex items-center">
              <EventButton :event="event" @select-event="selectEvent" class="absolute -left-16" />
              <div class="h-40 w-2 bg-gray-600"></div>
            </li>
          </ul>
        </div>
      </div>

      <!-- Desktop Event Details -->
      <div v-if="!isMobile" class="sm:w-10/12">
        <EventDetails :event-id="selectedEvent.eventId" v-if="selectedEvent" :event="selectedEvent" />
      </div>
    </div>

    <!-- Mobile Modal for Event Details -->
    <div
      v-if="isMobile && selectedEvent"
      class="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50 z-50"
      @click.self="selectedEvent = null"
    >
      <div class="relative bg-white rounded-lg shadow-lg w-[90%] max-h-[80%] overflow-y-auto p-6">
        <!-- Close Button -->
        <button class="absolute top-2 right-2 text-gray-500 hover:text-gray-800" @click="selectedEvent = null">
          <i class="fa-solid fa-times"></i>
        </button>

        <EventDetails :event-id="selectedEvent.eventId" :event="selectedEvent" />
      </div>
    </div>
  </div>
</template>
