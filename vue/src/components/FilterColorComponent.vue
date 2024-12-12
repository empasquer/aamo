<script setup lang="ts">
import { ref, watch } from 'vue';


interface ArtWorkTag {
  tagType: string;
  tagValue: string;
}

const props = defineProps<{
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

</script>

<template>
  <div class="filter-item flex flex-col">
    <h3
        @click="toggleColors"
        class="font-bold cursor-pointer text-lg text-gray-700 hover:text-[#4289a3]">
      Farve
    </h3>
    <div v-if="showColors" class="mt-2">
      <div v-for="tag in props.tags" :key="tag" class="mb-2">
        <label class="flex items-center space-x-2">
          <input
              type="checkbox"
              :id="tag"
              :value="tag"
              v-model="selectedTags"
              class="w-4 h-4"
          />
          <span class="text-gray-600">{{ tag }}</span>
        </label>
      </div>
    </div>
  </div>
</template>
