<script setup lang="ts">
import FilterSizeComponent from "./FilterSizeComponent.vue";
import FilterThemeComponent from "./FilterThemeComponent.vue";
import FilterColorComponent from "./FilterColorComponent.vue";
import SortPriceComponent from "./SortPriceComponent.vue";
import {computed, filter, ref} from "vue";

interface ArtWorkTag {
  tagType: string;
  tagValue: string;
}

const props = defineProps<{
  tags: ArtWorkTag[];
}>();

//TODO er det vigtigt her at 'size' står som SIZE som i enum?
const sizeTags = computed(() => {
  const uniqueSizeTags= Array.from(
      new Set(props.tags.
      filter((tag) => tag.tagType === 'SIZE')
          .map((tag) => tag.tagValue)
      )
  );
  return uniqueSizeTags.map((size) => (size));
});




const selectedFilters = ref({
  size: [] as string[],
});

const emit = defineEmits (['filter-applied']);

//Opdaterer filtrerings resultateerne.
const applyFilters = () => {
  emit('filter-applied', selectedFilters.value);
}

</script>

<template>
  <div>
    <FilterSizeComponent
    :tags="sizeTags"
    @filter-changed="(filters) => selectedFilters.size = filters"/>

    <!--<button @click="applyFilters" class="mt-4 p-2 bg-blue-500 text-white">SØG</button>-->

  </div>
</template>

<style scoped>

</style>