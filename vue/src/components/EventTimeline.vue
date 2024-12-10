<script setup lang="ts">
import { ref, onMounted, onBeforeUnmount } from 'vue';
import EventDetails from "./EventDetails.vue";
import EventButton from "./EventButton.vue";
import Headings from "./HeadingsComponent.vue";
import axios from "axios";

// Reactive state
const events = ref([]);
const selectedEventId = ref<number | null>(null);
const isMobile = ref(window.innerWidth <= 768);

// Methods
const selectEvent = (eventId: number) => {
  selectedEventId.value = eventId;
};

const displayFirstEvent = () => {
  // Set the first event only if no event has been selected
  if (events.value.length > 0 && selectedEventId.value === null) {
    selectedEventId.value = events.value[0].eventId;
  }
};

const handleResize = () => {
  isMobile.value = window.innerWidth <= 768;
};

// Lifecycle hooks
onMounted(() => {
  axios
      .get("http://localhost:8080/api/events")
      .then((response) => {
        events.value = response.data;
        displayFirstEvent(); // Set the first event if needed
      })
      .catch((error) => {
        console.error("Error fetching events:", error);
      });

  window.addEventListener("resize", handleResize);
});

onBeforeUnmount(() => {
  window.removeEventListener("resize", handleResize);
});
</script>


<template>
  <div id="events" class="events-container">
    <Headings text="EVENTS" :level="2" class="flex p-6"></Headings>

    <div v-if="isMobile" class="sm:w-10/12">
      <EventDetails :eventId="selectedEventId"></EventDetails>
    </div>

    <div class="timeline flex flex-col w-full sm:flex-row sm:p-4">
      <div class="flex flex-col items-center w-full sm:w-1/2 pt-4">
        <!-- Chevron Arrow -->
        <div>
          <i class="fa-solid fa-chevron-up text-gray-600 text-7xl"></i>
        </div>
        <div class="w-2 bg-gray-600" style="margin-top: -3em; height: 4em"></div>
        <!-- Event List -->
        <div class="overflow-y-auto w-full flex justify-center h-4/6">
          <ul>
            <li v-for="event in events" :key="event.eventId" class="relative flex items-center">
              <!-- Vertical Line -->
              <div class="h-40 w-2 bg-gray-600"></div>
              <!-- Event Button -->
              <EventButton :event="event" class="absolute" style="left: -7.4em" @select-event="selectEvent" />
            </li>
          </ul>
        </div>
      </div>
      <!-- Event Details -->
      <div v-if="!isMobile" class="sm:w-10/12">
        <EventDetails :eventId="selectedEventId"></EventDetails>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* Hide scrollbar for Webkit-based browsers (Chrome, Safari, Edge) */
.overflow-y-auto::-webkit-scrollbar {
  display: none;
}

/* Hide scrollbar for Firefox */
.overflow-y-auto {
  scrollbar-width: none; /* Firefox */
}

/* Ensure the container still scrolls */
.overflow-y-auto {
  overflow-y: auto; /* Keep scrolling enabled */
}
</style>
