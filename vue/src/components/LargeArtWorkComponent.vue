<script setup lang="ts">
import { ref } from 'vue';

interface ArtWork {
  artWorkId: number;
  title: string;
  description: string;
  price: number;
  isSold: boolean;
  media: {
    mediaUrl: string;
  };
  tags: ArtWorkTag[];
}

interface ArtWorkTag {
  tagType: string;
  tagValue: string;
}

const props = defineProps<{
  artWork: ArtWork;
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
      class="fixed inset-0 bg-black bg-opacity-60 flex items-center justify-center z-50">
    <div class="bg-white shadow-lg max-w-6xl w-full p-6">
      <div class="flex flex-col md:flex-row gap-4">

        <!-- Billedet -->
        <div class="flex-1">
          <img
              :src="artWork.media.mediaUrl"
              :alt="artWork.title || 'Art Work'"
              class="w-full h-auto rounded"
          />
        </div>

        <!-- Detaljer -->
        <div class="flex-1">
          <h2 class="text-2xl font-bold mb-4">{{ artWork.title }}</h2>
          <p class="mb-4">{{ artWork.description }}</p>
          <p class="mb-4">
            <span class="font-semibold">Pris:</span>
            {{ artWork.price }} DKK
            <span v-if="artWork.isSold">
              (Solgt)
            </span>
          </p>

          <hr class="my-4" />
          <p class="text-sm text-gray-500">Kontakt mig på: eva.aamo.ea@gmail.com</p>
        </div>
      </div>

      <!-- Luk knap -->
      <button
          @click="closeModal"
          class="absolute top-4 right-4 bg-red-500 text-white w-8 h-8 flex items-center justify-center"
      >
        &times;
      </button>
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
}

.bg-white {
  max-width: 90%; /* Justér bredde for større visning */
  max-height: 90%; /* Forhindrer overflow på små skærme */
  overflow-y: auto; /* Tilføj scroll, hvis indhold er for stort */
}
</style>