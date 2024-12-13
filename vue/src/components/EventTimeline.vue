<script lang="ts" setup>
import { ref, onMounted, watch, onUnmounted } from "vue";
import EventDetails from "./EventDetails.vue";
import EventButton from "./EventButton.vue";
import Headings from "./HeadingsComponent.vue";
import axios from "axios";

// Reactive states
const events = ref([]);
const selectedEvent = ref(null);
const isMobile = ref(window.innerWidth <= 768);
const loading = ref(true);

// Fetch events from the API
async function fetchEvents() {
  try {
    const response = await axios.get("http://localhost:8080/api/events");
    events.value = response.data;
    if (events.value.length > 0) {
      selectedEvent.value = events.value[0]; // Set the first event as default
    }
  } catch (error) {
    console.error("Error fetching events:", error);
  } finally {
    loading.value = false;
  }
}

// Select a specific event
function selectEvent(eventId: number) {
  const event = events.value.find((e) => e.eventId === eventId);
  if (event) {
    selectedEvent.value = event;
  }
}

// Handle window resize for mobile detection
function handleResize() {
  isMobile.value = window.innerWidth <= 768;
}

onMounted(() => {
  fetchEvents();
  window.addEventListener("resize", handleResize);
});

onUnmounted(() => {
  window.removeEventListener("resize", handleResize);
});
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

    <!-- Event List and Details -->
    <div v-else class="timeline flex flex-col sm:flex-row sm:p-4">
      <!-- Timeline Section -->
      <div class="timeline-section flex flex-col items-center w-full sm:w-1/2 pt-4">
        <i class="fa-solid fa-chevron-up text-gray-600 text-7xl"></i>
        <div class="w-2 bg-gray-600 -mt-12 h-16"></div>

        <!-- Event List -->
        <div class="event-list overflow-y-auto w-full flex justify-center h-4/6">
          <ul class="space-y-8">
            <li v-for="event in events" :key="event.eventId" class="relative flex items-center">
              <EventButton :event="event" @select-event="selectEvent" class="absolute -left-16" />
              <div class="h-40 w-2 bg-gray-600"></div>
            </li>
          </ul>
        </div>
      </div>

      <!-- Desktop Event Details -->
      <div v-if="!isMobile" class="event-details sm:w-10/12">
        <EventDetails :event-id="selectedEvent.eventId" :event="selectedEvent" v-if="selectedEvent" />
      </div>
    </div>

    <!-- Mobile Modal for Event Details -->
    <div
      v-if="isMobile && selectedEvent"
      class="modal fixed inset-0 flex items-center justify-center bg-black bg-opacity-50 z-50"
      @click.self="selectedEvent = null"
    >
      <div class="modal-content relative bg-white rounded-lg shadow-lg w-[90%] max-h-[80%] overflow-y-auto p-6">
        <!-- Close Button -->
        <button class="absolute top-2 right-2 text-gray-500 hover:text-gray-800" @click="selectedEvent = null">
          <i class="fa-solid fa-times"></i>
        </button>

        <EventDetails :event-id="selectedEvent.eventId" :event="selectedEvent" />
      </div>
    </div>
  </div>
</template>

<style scoped>
/* Styling for events container */
.events-container {
  display: flex;
  flex-direction: column;
}

/* Timeline and event list styling */
.timeline-section {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
}

.event-list {
  overflow-y: auto;
  width: 100%;
  display: flex;
  justify-content: center;
}

.event-details {
  width: 80%;
}

.modal {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: rgba(0, 0, 0, 0.5);
}

.modal-content {
  position: relative;
  background-color: white;
  border-radius: 8px;
  padding: 20px;
  width: 90%;
  max-height: 80%;
  overflow-y: auto;
}

button {
  cursor: pointer;
}

h2 {
  font-size: 24px;
  margin-bottom: 20px;
}
</style>
