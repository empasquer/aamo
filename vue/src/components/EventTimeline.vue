<script setup lang="ts">
import { ref, onMounted, onUnmounted, watch, computed } from "vue";
import axios from "axios";
import EventDetails from "./EventDetails.vue";
import EventButton from "./EventButton.vue";
import Headings from "./HeadingsComponent.vue";

// Reactive properties
const events = ref([]);
const selectedEventId = ref<number | string | null>(null);
const isMobile = ref(window.innerWidth <= 768);
const showModal = ref(false);

// Methods
const fetchEvents = async () => {
  try {
    const response = await axios.get("http://localhost:8080/api/events");
    events.value = response.data;
    if (events.value.length > 0) {
      selectedEventId.value = events.value[0].eventId;
    }
  } catch (error) {
    console.error("Error fetching events:", error);
  }
};

const selectEvent = (eventId: number | string) => {
  selectedEventId.value = eventId;
  showModal.value = true;
};

const closeModal = () => {
  showModal.value = false;
};

const handleResize = () => {
  isMobile.value = window.innerWidth <= 768;
};

// Lifecycle hooks
onMounted(() => {
  fetchEvents();
  window.addEventListener("resize", handleResize);
});

onUnmounted(() => {
  window.removeEventListener("resize", handleResize);
});
</script>

<template>
  <div id="events" class="events-container">
    <!-- Header -->
    <Headings text="EVENTS" :level="2" class="flex p-6" />

    <!-- Modal for Mobile View -->
    <div
      v-if="isMobile && showModal"
      @click.self="closeModal"
      class="fixed top-0 left-0 w-full h-full bg-black bg-opacity-70 flex justify-center items-center z-20"
    >
      <div class="relative bg-black bg-opacity-60 p-4 shadow-lg w-screen max-w-lg">
        <button @click="closeModal" class="absolute top-0 right-1 text-white text-3xl p-4" aria-label="Close Modal">&times;</button>
        <EventDetails :eventId="selectedEventId" color="white" />
      </div>
    </div>

    <!-- Timeline -->
    <div class="timeline flex flex-col w-full sm:flex-row sm:p-4">
      <!-- Event Buttons -->
      <div class="flex flex-col items-center w-full sm:w-1/2 pt-4">
        <div>
          <i class="fa-solid fa-chevron-up text-gray-600 text-7xl"></i>
        </div>
        <div class="w-2 bg-gray-600" style="margin-top: -3em; height: 4em"></div>

        <div class="overflow-y-auto w-full flex justify-center h-4/6">
          <ul>
            <li v-for="event in events" :key="event.eventId" class="relative flex items-center">
              <div class="h-40 w-2 bg-gray-600"></div>
              <EventButton :event="event" class="absolute" style="left: -7.4em" @select-event="selectEvent" />
            </li>
          </ul>
        </div>
      </div>

      <!-- Event Details for Desktop -->
      <div v-if="!isMobile" class="sm:w-10/12">
        <EventDetails :eventId="selectedEventId" />
      </div>
    </div>
  </div>
</template>

<style scoped>
/* Modal styles */
.bg-opacity-70 {
  background: rgba(0, 0, 0, 0.7);
}

button[aria-label="Close Modal"] {
  background: none;
  border: none;
  cursor: pointer;
}

/* Hide scrollbar for Webkit-based browsers (Chrome, Safari, Edge) */
.overflow-y-auto::-webkit-scrollbar {
  display: none;
}

/* Hide scrollbar for Firefox */
.overflow-y-auto {
  scrollbar-width: none;
}

/* Ensure the container still scrolls */
.overflow-y-auto {
  overflow-y: auto;
}
</style>
