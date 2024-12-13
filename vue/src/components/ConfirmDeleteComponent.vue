<script setup lang="ts">
import { ref } from "vue";
import FormButton from "./FormButtonComponent.vue";

const props = defineProps<{
  toDelete: {
    toDeleteId: number;
    title: string;
  };
}>();

const emit = defineEmits(["close", "confirm-delete"]);
const isLoading = ref(false);

const confirmDelete = async () => {
  isLoading.value = true;

  try {
    emit("confirm-delete", props.toDelete.toDeleteId);
  } catch (error) {
    console.error("Error during deletion:", error);
  } finally {
    isLoading.value = false;
  }
};
</script>

<template>
  <div class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center">
    <div class="bg-white p-6 shadow-lg text-center">
      <h3 class="text-xl font-bold mb-4">Confirm Delete</h3>
      <p>Are you sure you want to delete "{{ props.title }}"?</p>

      <div class="mt-6 flex justify-center gap-4">
        <!-- Cancel Button -->
        <button class="bg-gray-300 px-4 py-2" @click="$emit('close')">Cancel</button>
        <!-- Delete Button -->
        <FormButton
            :loading="isLoading"
            @click="confirmDelete"
            class="bg-red-600 text-white px-4 py-2"
        >
          Delete
        </FormButton>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* Add additional styles if necessary */
</style>
