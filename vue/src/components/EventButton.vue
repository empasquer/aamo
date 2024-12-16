<script lang="ts">
export default {
  name: "EventButton",
  props: {
    event: {
      type: Object,
      required: true,
    },
  },
  methods: {
    handleClick() {
      this.$emit("select-event", this.event.eventId);
    },
  },
};
</script>

<template>
  <button @click="handleClick" class="flex items-center bg-transparent br-0 p-0 border-none h-auto w-auto sm:whitespace-nowrap space-x-4">
    <span>{{ event.formattedStartDate }}</span>
    <i class="fa-solid fa-diamond text-gray-600 text-5xl"></i>
    <span>{{ event.title }}</span>
  </button>
</template>
