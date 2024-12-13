<script setup>
import { defineProps, computed } from "vue";

const props = defineProps({
  children: {
    type: String,
    default: "",
  },
  className: {
    type: String,
    default: "",
  },
  contentStyle: {
    type: Object,
    default: () => ({}),
  },
  contentArrowStyle: {
    type: Object,
    default: () => ({}),
  },
  date: {
    type: String,
    required: true,
  },
  dateClassName: {
    type: String,
    default: "",
  },
  icon: {
    type: String,
    default: "",
  },
  iconStyle: {
    type: Object,
    default: () => ({}),
  },
  iconOnClick: {
    type: Function,
    default: null,
  },
  onContentClick: {
    type: Function,
    default: null,
  },
  position: {
    type: String,
    default: "left",
  },
});

// Define the classes dynamically based on the position prop
const elementClass = computed(() => ({
  "vertical-timeline-element--left": props.position === "left",
  "vertical-timeline-element--right": props.position === "right",
}));
</script>

<template>
  <div :class="elementClass" :style="style" class="vertical-timeline-element">
    <span class="icon" :style="iconStyle" @click="onIconClick">{{ icon }}</span>
    <div class="content" @click="onContentClick">
      <div class="content-arrow" :style="contentArrowStyle"></div>
      <slot></slot>
      <!-- Event details go here -->
      <div class="date" :class="dateClassName">{{ date }}</div>
    </div>
  </div>
</template>

<style scoped>
.vertical-timeline-element {
  display: flex;
  flex-direction: column;
  position: relative;
  margin: 10px 0;
}

.icon {
  background: #33b5e5;
  color: #fff;
  padding: 10px;
  border-radius: 50%;
}

.content {
  background: #ffffff;
  padding: 10px;
  border-radius: 5px;
}

.content-arrow {
  border-right: 7px solid #33b5e5;
}

.date {
  font-size: 12px;
  color: #888;
}
</style>
