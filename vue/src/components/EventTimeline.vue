<script lang="ts">
import EventDetails from './EventDetails.vue';
import EventButton from './EventButton.vue';
import axios from 'axios';

export default {
  name: 'EventTimeline',
  components: {
    EventDetails,
    EventButton,
  },
  data() {
    return {
      events: [],
      selectedEventId: null,
    };
  },
  methods: {
    selectEvent(eventId) {
      this.selectedEventId = eventId;
    },
  },
  mounted() {
    axios
        .get('http://localhost:8080/api/events')
        .then((response) => {
          this.events = response.data;
          console.log(response.data);
        })
        .catch((error) => {
          console.error('Error fetching events:', error);
        });
  },
};
</script>

<template>
  <div class="events-container">
    <div class="flex flex-box">
      <div class="flex flex-col items-center w-96">
        <!-- Chevron Arrow -->
        <div>
          <i class="fa-solid fa-chevron-up text-gray-600 text-7xl"></i>
        </div>
        <div class="w-2 bg-gray-600" style="margin-top: -3em; height: 3em;"></div>
        <!-- Event List -->
        <ul>
          <li v-for="event in events" :key="event.eventId" class="relative flex items-center">
            <!-- Vertical Line -->
            <div class="h-40 w-2 bg-gray-600"></div>
            <!-- Event Button -->
            <EventButton
                :event="event"
                class="absolute top-1/2 left-[-118px] transform -translate-y-1/2 -translate-x-0"
                @select-event="selectEvent"
            />
          </li>
        </ul>
      </div>
      <!-- Event Details -->
      <div v-if="selectedEventId">
        <EventDetails :eventId="selectedEventId"></EventDetails>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* Add styles here */
</style>
