<script setup>
import { defineProps } from "vue";

const props = defineProps({
  animate: {
    type: Boolean,
    default: true,
  },
  className: {
    type: String,
    default: "",
  },
  layout: {
    type: String,
    default: "2-columns",
    validator(value) {
      return ["1-column-left", "1-column", "2-columns", "1-column-right"].includes(value);
    },
  },
  lineColor: {
    type: String,
    default: "#FFF",
  },
});
</script>
<template>
  <div
    :class="[
      'vertical-timeline',
      {
        'vertical-timeline--animate': animate,
        'vertical-timeline--two-columns': layout === '2-columns',
        'vertical-timeline--one-column-left': layout === '1-column' || layout === '1-column-left',
        'vertical-timeline--one-column-right': layout === '1-column-right',
      },
      className,
    ]"
    :style="{ '--line-color': lineColor }"
  >
    <slot></slot>
  </div>
</template>

<style scoped>
.vertical-timeline {
  position: relative;
  padding-left: 50px;
  margin-left: 20px;
  width: 100%;
}

.vertical-timeline--animate .vertical-timeline-element {
  animation: bounceIn 1s ease-in-out;
}

.vertical-timeline--two-columns .vertical-timeline-element--left {
  float: left;
  width: 45%;
}

.vertical-timeline--two-columns .vertical-timeline-element--right {
  float: right;
  width: 45%;
}

.vertical-timeline--one-column-left .vertical-timeline-element--left {
  width: 100%;
}

.vertical-timeline--one-column-right .vertical-timeline-element--right {
  width: 100%;
}

.vertical-timeline-element-content-arrow {
  position: absolute;
  left: 50%;
  top: 0;
  transform: translateX(-50%);
}

@keyframes bounceIn {
  0% {
    opacity: 0;
    transform: translateY(-20px);
  }
  100% {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>
