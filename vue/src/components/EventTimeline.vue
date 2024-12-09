<script lang="ts">
import EventDetails from './EventDetails.vue';
import EventButton from './EventButton.vue';
import Headings from "./HeadingsComponent.vue";
import axios from 'axios';

export default {
  name: 'EventTimeline',
  components: {
    EventDetails,
    EventButton,
    Headings,
  },
  data() {
    return {
      events: [],
      selectedEventId: null,
      isMobile: window.innerWidth <= 768,
    };
  },
  methods: {
    selectEvent(eventId) {
      this.selectedEventId = eventId;
    },
    displayFirstEvent() {
      if (this.events.length > 0) {
        this.selectedEventId = this.events[0].eventId;
      }
    },
    handleResize() {
      this.isMobile = window.innerWidth <= 768;
    },
  },
  mounted() {
    axios
        .get('http://localhost:8080/api/events')
        .then((response) => {
          this.events = response.data;
          console.log(response.data);
          this.displayFirstEvent();
        })
        .catch((error) => {
          console.error('Error fetching events:', error);
        });

    window.addEventListener('resize', this.handleResize);
  },
  beforeDestroy() {
    // Clean up the event listener when the component is destroyed
    window.removeEventListener('resize', this.handleResize);
  },

};
</script>

<template>
  <div class="events-container ">
    <Headings text="EVENTS" :level=2 class="flex p-6"></Headings>

    <div  v-if="isMobile"  class="sm:w-10/12">
      <EventDetails :eventId="selectedEventId"></EventDetails>
    </div>

    <div class="timeline flex flex-col w-full sm:flex-row sm:p-4">
      <div class="flex flex-col  items-center w-full sm:w-1/2 pt-4 ">

        <!-- Chevron Arrow -->
        <div>
          <i class="fa-solid fa-chevron-up text-gray-600 text-7xl"></i>
        </div>
        <div class="w-2 bg-gray-600" style="margin-top: -3em; height: 4em;"></div>
        <!-- Event List -->
        <div class="overflow-y-auto w-full flex justify-center h-4/6">
        <ul >
          <li v-for="event in events" :key="event.eventId" class="relative flex items-center">
            <!-- Vertical Line -->
            <div class="h-40 w-2 bg-gray-600"></div>
            <!-- Event Button -->
            <EventButton
                :event="event"
                class="absolute "style="left: -7.4em"
                @select-event="selectEvent"
            />
          </li>
        </ul>
      </div>
      </div>
      <!-- Event Details -->
      <div v-if="!isMobile"  class="sm:w-10/12">
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