<script setup lang="ts">
const props = defineProps<{}>();
</script>

<!-- FIXME: placeholder nav for now -->
<template>
  <nav class="flex flex-col justify-end">
    <div class="flex flex-grow justify-end space-x-24 px-8">
      <span class="font-bold">EVENTS</span>
      <!-- <span>|</span> -->
      <span class="font-bold">MALERIER</span>
      <!-- <span>|</span> -->
      <span class="font-bold">ØVRIGE VÆRKER</span>
      <!-- <span>|</span> -->
      <span class="font-bold">OM MIG</span>
    </div>

    <span class="w-full h-[2px] bg-black mt-2 block"></span>
  </nav>
</template>
