<script setup lang="ts">
const props = defineProps<{
  color?: string;
  href: string;
  image: string;
  text: string;
}>();

function handleClick() {
  window.location.href = props.href;
}
</script>

<template>
  <div>
    <button @click="handleClick" class="w-full h-full bg-cover bg-center bg-no-repeat" :style="{ backgroundImage: 'url(' + props.image + ')' }">
      <div class="absolute inset-0 bg-black bg-opacity-70 flex items-center justify-center">
        <span :style="{ color: props.color || '#fff' }" class="text-white text-lg font-semibold">
          {{ props.text }}
        </span>
      </div>
    </button>
  </div>
</template>

<style scoped>
button {
  position: relative;
  overflow: hidden;
}

button:hover .bg-black {
  background-color: rgba(0, 0, 0, 0.9);
}
</style>
