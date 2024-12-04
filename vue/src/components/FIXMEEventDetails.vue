<script setup lang="ts">
import { ref, watch, onMounted } from "vue";
import axios from "axios";

const props = defineProps<{
  eventId: string;
}>();

const event = ref({
  mediaUrls: [],
});

const isYoutubeLink = (url: string) => {
  return typeof url === "string" && (url.includes("youtube.com") || url.includes("youtu.be"));
};

const getYoutubeEmbedUrl = (url: string) => {
  const videoId = url.match(/(?:v=|\/)([a-zA-Z0-9_-]{11})/);
  return videoId ? `https://www.youtube.com/embed/${videoId[1]}` : "";
};

// TODO: FETCH RIGTIG ENDPOINT
const fetchEvent = (eventId: string) => {
  axios
    .get(`/api/event?eventId=${eventId}`)
    .then((response) => {
      event.value = response.data;
    })
    .catch((error) => {
      console.error("Error fetching the event:", error);
    });
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
  <div class="flex flex-col sm:flex-row items-center sm:items-start gap-6">
    <!-- <p>{{ event.title }}</p>
    <p>{{ event.startDate }}</p>
    <p>{{ event.endDate }}</p>
    <p>{{ event.description }}</p> -->

    <ul>
      <li v-for="media in event.mediaUrls" :key="media.mediaId">
        <template v-if="isYoutubeLink(media.mediaUrl)">
          <iframe :src="getYoutubeEmbedUrl(media.mediaUrl)" class="video-embed"></iframe>
        </template>
        <template v-else>
          <img class="w-48 md:w-64 lg:w-82" :src="media.mediaUrl" alt="Event picture" />
        </template>
      </li>
    </ul>
  </div>
</template>

<style scoped></style>
