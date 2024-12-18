<script setup lang="ts">
import { ref } from 'vue';

const props = defineProps<{

}>();

const isModalVisible = ref(true);

const emit = defineEmits<{
  (event: 'close'): void;
}>()

const closeModal = () => {
  isModalVisible.value = false;
  emit('close');
};
</script>

<template>
  <div
      v-if="isModalVisible"
      @click="closeModal"
      class="fixed inset-0 bg-black bg-opacity-60 flex items-center justify-center z-50 cursor-pointer">
    <div class="bg-black-modal shadow-lg  w-full p-6"
         @click.stop
    >
      <div class="flex flex-col md:flex-row gap-4">

        <slot>

        </slot>

      <!-- Luk knap -->
      <button
          @click="closeModal"
          class="absolute top-4 right-4 bg-red-700 text-white w-8 h-8 flex items-center justify-center hover:bg-red-800"
      >
        &times;
      </button>
    </div>
  </div>
  </div>
</template>


<style scoped>
.fixed {
  position: fixed;
  inset: 0; /* Strækker sig over hele skærmen */
  background-color: rgba(0, 0, 0, 0.7); /* Mørk baggrund */
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 50; /* Sørg for, den ligger øverst */

  max-width: 100%;
}

.bg-black-modal {
  height: 30em;
  max-height: 80%; /* Forhindrer overflow på små skærme */
  overflow-y: hidden; /* Tilføj scroll, hvis indhold er for stort */
  background-color: black;
  opacity: 80%;
  cursor: default;
  overflow-x: hidden;
}
</style>