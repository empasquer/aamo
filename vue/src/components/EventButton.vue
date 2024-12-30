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
      // Emit an event to the parent when the button is clicke
      this.$emit("select-event", this.event.eventId);
    },
  },
};
</script>

<template>
  <button @click="handleClick" class="grid grid-cols-[25vw_10vw_45vw] gap-4 items-center bg-transparent border-none p-4 w-full w-[95vw]">
    <span class="col-span-1">{{ event.formattedStartDate }}</span>

    <i class="col-span-1 fa-solid fa-diamond text-gray-600 text-5xl"></i>

    <span class="col-span-1 text-left">{{ event.title }}</span>
  </button>
</template>
