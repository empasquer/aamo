<script setup lang="ts">
import { ref, onMounted, onUnmounted, watch, computed } from "vue";
import axios from "axios";
import EventDetails from "./EventDetails.vue";
import EventButton from "./EventButton.vue";
import Headings from "./HeadingsComponent.vue";

const events = ref([]);
const selectedEventId = ref<number | string | null>(null);
const isMobile = ref(window.innerWidth <= 768);
const showModal = ref(false);

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
    <Headings text="EVENTS" :level="2" class="flex p-6" />

    <!-- Modal for mobile view -->
    <div
      v-if="isMobile && showModal"
      @click.self="closeModal"
      class="fixed top-0 left-0 w-full h-full bg-black bg-opacity-70 flex justify-center items-center z-20"
    >
      <div class="relative bg-black bg-opacity-60 p-4 shadow-lg w-screen max-w-lg">
        <button @click="closeModal" class="absolute top-0 right-1 text-white text-3xl p-4 bg-transparent" aria-label="Close Modal">&times;</button>
        <EventDetails :eventId="selectedEventId" color="white" />
      </div>
    </div>

    <!-- Timeline and Event Buttons Section -->
    <div class="timeline flex flex-col w-full sm:flex-row sm:p-4">
      <div class="flex flex-col items-center w-full sm:w-1/2 pt-4 relative">
        <!-- Chevron Icon -->
        <div class="absolute left-[-2.55rem] w-full text-center mb-2">
          <i class="fa-solid fa-chevron-up text-gray-600 text-7xl"></i>
        </div>

        <!-- Scrollable Event Buttons positioned above the line -->
        <div id="single-events" class="overflow-y-scroll absolute top-[5rem] w-full max-h-[70vh] flex justify-center items-center z-10 box-border">
          <ul class="flex flex-col justify-center items-center space-y-12">
            <p>1</p>
            <li v-for="event in events" :key="event.eventId" class="relative flex items-center justify-center">
              <EventButton :event="event" @select-event="selectEvent" />
            </li>
            <li v-for="event in events" :key="event.eventId" class="relative flex items-center justify-center">
              <EventButton :event="event" @select-event="selectEvent" />
            </li>
          </ul>
        </div>

        <!-- Vertical Line -->
        <div id="line" class="absolute left-[10.65rem] top-[2rem] h-[75vh] w-[8px] bg-gray-600 z-0"></div>
      </div>

      <div v-if="!isMobile" class="sm:w-10/12">
        <EventDetails :eventId="selectedEventId" />
      </div>
    </div>
  </div>
</template>
