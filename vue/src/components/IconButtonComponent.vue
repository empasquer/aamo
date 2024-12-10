<script setup lang="ts">
// Define props for the component
const props = defineProps<{
  color?: string; // Custom text color for the icon
  height: string; // Height of the icon (e.g., '25px')
  variant: "instagram" | "mail" | "arrow-circle"; // Determines which icon to display
  scrollToId?: string; // Target ID for scrolling (only relevant for arrow-circle)
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
  const header = document.querySelector("header");
  const headerHeight = header ? header.offsetHeight : 0;

  const topOffset = window.innerWidth >= 768 ? headerHeight + 0 : headerHeight + 10;

  if (element) {
    window.scrollTo({
      top: element.offsetTop - topOffset,
      behavior: "smooth",
    });
  }
}
</script>

<template>
  <div class="w-fit h-fit">
    <button
      @click="handleClick"
      :style="{ width: props.height, height: props.height }"
      class="bg-transparent border-0 p-0 flex items-center justify-center"
    >
      <!-- Font Awesome Outline Icons -->
      <i v-if="variant === 'instagram'" class="fa-brands fa-instagram" :style="{ fontSize: props.height, color: props.color || '#4A4A4A' }"></i>

      <i v-if="variant === 'mail'" class="fa-regular fa-envelope" :style="{ fontSize: props.height, color: props.color || '#4A4A4A' }"></i>

      <!-- Custom SVG for Arrow Circle -->
      <svg
        v-if="variant === 'arrow-circle'"
        viewBox="0 0 50 50"
        fill="none"
        xmlns="http://www.w3.org/2000/svg"
        :style="{ width: props.height, height: props.height }"
      >
        <path
          d="M15.5 24.5L24.5 33.5M24.5 33.5L33.5 24.5M24.5 33.5V15.5M47 24.5C47 36.9264 36.9264 47 24.5 47C12.0736 47 2 36.9264 2 24.5C2 12.0736 12.0736 2 24.5 2C36.9264 2 47 12.0736 47 24.5Z"
          :stroke="props.color || '#F0F6F9'"
          stroke-width="4"
          stroke-linecap="round"
          stroke-linejoin="round"
        />
      </svg>
    </button>
  </div>
</template>
