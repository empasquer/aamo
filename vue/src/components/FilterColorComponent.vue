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
  <div>
    <!-- Klikbar overskrift -->
    <h3 @click="toggleColors" class="font-bold cursor-pointer">Farver</h3>
    <!-- Skjulte muligheder -->
    <div v-if="showColors" class="mt-2">
      <div v-for="tag in props.tags" :key="tag">
        <label>
          <input
              type="checkbox"
              :id="tag"
              :value="tag"
              v-model="selectedTags"
          />
          {{ tag }}
        </label>
      </div>
      <!-- <button @click="onFilterChange" class="mt-4 p-2 bg-blue-500 text-white">SØG</button> -->
    </div>
  </div>
</template>
