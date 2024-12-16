<script setup lang="ts">
import { ref, watch } from 'vue';


const sortOptions = [
  { label: "Fra lav til høj", value: "asc" },
  { label: "Fra høj til lav", value: "desc" },
  { label: "Ingen sortering", value: null },
];

const showPrices = ref(false);
const selectedSortOrder = ref<string | null>(null);

const props = defineProps<{
  closeAll: boolean;
}>();

watch(
    () => props.closeAll,
    (newVal) => {
      if (newVal) {
        showPrices.value = false; // Close the menu if closeAll is true
      }
    }
);

const togglePrices = () => {
  showPrices.value = !showPrices.value;
}

const emit = defineEmits(['filter-changed']);

const onFilterChange = () => {
  emit('filter-changed', {
    sortOrder: selectedSortOrder.value,
  });
};

watch([selectedSortOrder], onFilterChange);
</script>

<template>
  <div class="filter-item relative flex flex-col">
    <h3
        @click="togglePrices"
        class="font-bold relative cursor-pointer text-lg text-gray-700 hover:text-[#4289a3]">
      Pris
    </h3>
    <div v-if="showPrices"
         class="absolute bg-black bg-opacity-60 shadow-md top-10 mt-5 left-[-14px] p-5 z-10 min-w-max">
      <div v-for="option in sortOptions" :key="option.value" class="mb-2">
        <label class="flex items-center space-x-2">
          <input
              type="radio"
              :value="option.value"
              v-model="selectedSortOrder"
              class="w-5 h-5 mt-3 text-white bg-black border-white focus:ring-white checked:bg-[#4289a3]"
          />
          <span class="text-white mt-3 font-bold">{{ option.label }}</span>
        </label>
      </div>


    </div>
  </div>
</template>

<style scoped>
.filter-item {
  margin-bottom: 16px;
}
</style>
