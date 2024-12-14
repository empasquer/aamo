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
  closeAll: boolean;
  tags: ArtWorkTag[];
}>();

const sizeTags = computed(() => {
  const uniqueSizeTags= Array.from(
      new Set(props.tags.
      filter((tag) => tag.tagType === 'SIZE')
          .map((tag) => tag.tagValue)
      )
  );
  return uniqueSizeTags.map((size) => (size));
});

const themeTags = computed(() => {
  const uniqueThemeTags= Array.from(
      new Set(props.tags.
          filter((tag) => tag.tagType === 'THEME')
              .map((tag) => tag.tagValue)
      )
  );
  return uniqueThemeTags.map((theme) => (theme));
});

const colorTags = computed(() => {
  const uniqueColorTags= Array.from(
      new Set(props.tags.
          filter((tag) => tag.tagType === 'COLOR')
              .map((tag) => tag.tagValue)
      )
  );
  return uniqueColorTags.map((color) => (color));
});


const selectedFilters = ref({
  size: [] as string[],
  theme: [] as string[],
  color: [] as string[],
  price: "" as string,
});

const closeAllMenus = ref(false);

const emit = defineEmits (['filter-applied']);


const applyFilters = () => {
  emit("filter-applied", selectedFilters.value);
  closeAllMenus.value = true; // Trigger menus to close
  setTimeout(() => {
    closeAllMenus.value = false; // Reset for future openings
  });
};

</script>

<template>
  <div class="filter-sort-menu p-4">
    <div class="filter-options flex gap-16 flex-wrap">
      <FilterSizeComponent
          :tags="sizeTags"
          :closeAll="closeAllMenus"
          @filter-changed="(filters) => selectedFilters.size = filters"
      />
      <FilterThemeComponent
          :tags="themeTags"
          :closeAll="closeAllMenus"
          @filter-changed="(filters) => selectedFilters.theme = filters"
      />
      <FilterColorComponent
          :tags="colorTags"
          :closeAll="closeAllMenus"
          @filter-changed="(filters) => selectedFilters.color = filters"
      />
      <SortPriceComponent
          :closeAll="closeAllMenus"
          @filter-changed="(filters) => {
          selectedFilters.sortOrder = filters.sortOrder;
        }"
      />
      <button
          @click="applyFilters"
          class="mt-0 px-4 py-1 bg-transparent border-[2.8px] border-gray-700 text-gray-700 font-bold text-base hover:border-[#4289a3] hover:text-[#4289a3] transition duration-300 align-middle transform -translate-y-1"
      >
        SØG
      </button>
    </div>
  </div>
</template>

<style scoped>

</style>