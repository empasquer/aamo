<script lang="ts" setup>
import { defineProps, defineEmits } from "vue";

const props = defineProps({
  event: {
    type: Object,
    required: true,
  },
  isActive: {
    type: Boolean,
    default: false,
  },
});

const emit = defineEmits(["select-event"]);

function handleClick() {
  emit("select-event", props.event.eventId);
}
</script>

<template>
  <button
    @click="handleClick"
    class="relative flex flex-col items-center justify-center bg-transparent p-2 h-12 w-12 transform rotate-45 border border-gray-600 hover:bg-gray-300 focus:ring-2 focus:ring-blue-500"
  >
    <span class="absolute transform -rotate-45 text-sm text-gray-800">
      {{ event.title }}
    </span>
  </button>
</template>
