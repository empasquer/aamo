<script setup>
import { ref, onMounted } from "vue";
import VerticalTimeline from "./VerticalTimelineComponent.vue";
import VerticalTimelineElement from "./VerticalTimelineElementComponent.vue";
import axios from "axios"; // Import axios for API calls

// Define reactive events array
const events = ref([]);

// Fetch events from the API
const fetchEvents = async () => {
  try {
    const response = await axios.get("http://localhost:8080/api/events");
    events.value = response.data; // Assuming the API returns an array of events
  } catch (error) {
    console.error("Failed to fetch events:", error);
  }
};

// Call the API on component mount
onMounted(() => {
  fetchEvents();
});

// Handle event click
const handleEventClick = (event) => {
  console.log("Event clicked:", event);
};
</script>

<template>
  <VerticalTimeline :animate="true" :layout="'2-columns'">
    <VerticalTimelineElement
      v-for="event in events"
      :key="event.eventId"
      :date="event.formattedStartDate"
      :className="'vertical-timeline-element--work'"
      :contentStyle="{ background: 'rgb(33, 150, 243)', color: '#fff' }"
      :contentArrowStyle="{ borderRight: '7px solid rgb(33, 150, 243)' }"
      :icon="'🎨'"
      :iconStyle="{ background: 'rgb(33, 150, 243)', color: '#fff' }"
      @click="handleEventClick(event)"
    >
      <h3>{{ event.title }}</h3>
      <h4>Location: {{ event.title }}</h4>
      <!-- Placeholder subtitle, adjust as needed -->
      <p>{{ event.description || "No description available" }}</p>
      <!-- Add description if available -->
    </VerticalTimelineElement>
  </VerticalTimeline>
</template>
