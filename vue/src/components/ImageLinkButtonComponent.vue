<script setup lang="ts">
const props = defineProps<{
  color?: string;
  href: string;
  image: string;
  text: string;
}>();

function handleClick() {
  window.open(props.href, "_blank");
}
</script>

<template>
  <div class="h-[300px] relative">
    <button @click="handleClick" class="w-full h-full bg-[url(props.image)] bg-cover bg-center bg-no-repeat">
      <div class="absolute inset-0 bg-black bg-opacity-50 flex items-center justify-center">
        <span :style="{ color: props.color || '#fff' }" class="text-white text-lg font-semibold"> {{ text }}</span>
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
