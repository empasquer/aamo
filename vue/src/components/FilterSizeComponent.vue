<script setup lang="ts">
import { ref, watch } from 'vue';


interface ArtWorkTag {
  tagType: string;
  tagValue: string;
}

const props = defineProps<{
  tags: ArtWorkTag[];
}>();



//TODO her skal vi kun hente tagType hvor enummet er SIZE og så vise alle de tagValues der følger med den
const selectedTags = ref<string[]>([]);

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
    <h3 class="font-bold">Størrelse</h3>
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
  </div>
</template>
