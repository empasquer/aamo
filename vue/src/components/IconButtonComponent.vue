<script setup lang="ts">
import { defineProps } from "vue";
import InstagramIconComponent from "./icons/InstagramIconComponent.vue";
import MailIconComponent from "./icons/MailIconComponent.vue";
import ArrowCircleIconComponent from "./icons/ArrowCircleIconComponent.vue";

// Define props for the component
const props = defineProps<{
  color?: string;
  height?: string;
  width?: string;
  variant: "instagram" | "mail" | "arrow-circle";
  scrollToId?: string;
}>();

// Methods
function handleClick() {
  const INSTAGRAM_URL = "https://www.instagram.com/eva_aamo/";
  const MAIL_ADDRESS = "mailto:Eva.aamo.ea@gmail.com";

  if (props.variant === "instagram") {
    window.open(INSTAGRAM_URL, "_blank");
  } else if (props.variant === "mail") {
    window.location.href = MAIL_ADDRESS;
  } else if (props.variant === "arrow-circle" && props.scrollToId) {
    scrollToElement();
  }
}

function scrollToElement() {
  const element = document.getElementById(props.scrollToId || "");
  if (element) {
    element.scrollIntoView({
      behavior: "smooth",
      block: "start",
      inline: "nearest",
    });
  }
}
</script>

<template>
  <button @click="handleClick" :class="[color || 'text-gray-700', height || 'h-6', width || 'w-6']" class="inline-block">
    <!-- Instagram Icon -->
    <InstagramIconComponent v-if="variant === 'instagram'" />
    <!-- Mail Icon -->
    <MailIconComponent v-if="variant === 'mail'" />
    <!-- Arrow Circle Icon -->
    <ArrowCircleIconComponent v-if="variant === 'arrow-circle'" />
  </button>
</template>

<style scoped>
/* Add component-specific styles if needed */
</style>
