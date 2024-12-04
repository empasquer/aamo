<script setup lang="ts">
import { ref, onMounted } from "vue";
import axios from "axios";
// import EventDetails from './event-details.vue';

const events = ref([]);
const selectedEventId = ref<number | null>(null);

const selectEvent = (eventId: number) => {
  selectedEventId.value = eventId;
};

// Fetch the list of events when the component is mounted
onMounted(() => {
  axios
    .get("/api/events")
    .then((response) => {
      events.value = response.data;
    })
    .catch((error) => {
      console.error("Error fetching events", error);
    });
});
</script>

<template>
  <div class="events-container">
    <!-- Event List -->
    <div class="event-list">
      <ul>
        <li v-for="event in events" :key="event.eventId">
          <!-- Button to select an event -->
          <button @click="selectEvent(event.eventId)">
            {{ event.startDate }}
          </button>
        </li>
      </ul>
    </div>

    <!-- Event Details -->
    <div v-if="selectedEventId">
      <EventDetails :eventId="selectedEventId" />
    </div>
  </div>
</template>

<style scoped>
/* Add component-specific styles if needed */
</style>
