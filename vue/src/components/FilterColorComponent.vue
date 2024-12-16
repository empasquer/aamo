<script setup lang="ts">
import { ref, watch } from 'vue';


interface ArtWorkTag {
  tagType: string;
  tagValue: string;
}

const props = defineProps<{
  closeAll: boolean;
  tags: ArtWorkTag[];
}>();


const showColors = ref(false);
const selectedTags = ref<string[]>([]);

const toggleColors = () => {
  showColors.value = !showColors.value;
}

const toggleTag = (tag: string) => {
  if (selectedTags.value.includes(tag)) {
    selectedTags.value = selectedTags.value.filter((t) => t !== tag);
  } else {
    selectedTags.value.push(tag);
  }
};

const emit = defineEmits(['filter-changed']);
const onFilterChange = () => emit('filter-changed', selectedTags.value);

watch(selectedTags, onFilterChange);

watch(
    () => props.closeAll,
    (newVal) => {
      if (newVal) {
        showColors.value = false;
      }
    }
);

</script>

<template>
  <div class="filter-item relative flex flex-col">
    <h3
        @click="toggleColors"
        class="font-bold relative cursor-pointer text-lg text-gray-700 hover:text-[#4289a3]">
      Farve
    </h3>
    <div v-if="showColors"
         class="absolute bg-black bg-opacity-60 shadow-md top-10 mt-5 left-[-14px] p-5 z-10">
      <div v-for="tag in props.tags" :key="tag" class="mb-2">
        <label class="flex items-center space-x-2">
          <input
              type="checkbox"
              :id="tag"
              :value="tag"
              v-model="selectedTags"
              class="w-5 h-5 mt-3 text-white bg-black border-white focus:ring-white checked:bg-[#4289a3]"
          />
          <span class="text-white mt-3 font-bold">{{ tag }}</span>
        </label>
      </div>
    </div>
  </div>
</template>
