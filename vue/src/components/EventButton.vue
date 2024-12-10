<script setup lang="ts">

// Define the event prop
const props = defineProps({
  event: {
    type: Object as () => { eventId: number; formattedStartDate: string; title: string },
    required: true,
  },
});

// Define the emit function
const emit = defineEmits<{
  (event: 'select-event', eventId: number): void;
}>();

// Method to handle button click and emit the event to the parent
const handleClick = () => {
  emit('select-event', props.event.eventId);
};
</script>

<template>
  <button
      @click="handleClick"
      class="flex items-center bg-transparent br-0 p-0 border-none h-auto w-auto sm:whitespace-nowrap space-x-4"
  >
    <span>{{ props.event.formattedStartDate }}</span>
    <i class="fa-solid fa-diamond text-gray-600 text-5xl"></i>
    <span>{{ props.event.title }}</span>
  </button>
</template>

<style scoped>
/* Add styles specific to EventButton here if needed */
</style>
