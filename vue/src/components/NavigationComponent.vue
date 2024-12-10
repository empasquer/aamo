<script setup lang="ts">
import { useRouter } from "vue-router";

const router = useRouter();
const isActive = (path: string) => router.currentRoute.value.path === path;
</script>

<template>
  <nav class="flex flex-col justify-end">
    <div class="flex flex-grow justify-end space-x-8 md:space-x-24 px-4 md:px-8">
      <router-link
        :to="{ path: '/om-mig', hash: '#events' }"
        class="font-bold"
        :class="{ 'text-[#8baaac]': isActive('/om-mig'), 'hover:text-gray-700': true }"
      >
        EVENTS
      </router-link>
      <router-link to="/galleri" class="font-bold" :class="{ 'text-[#8baaac]': isActive('/galleri'), 'hover:text-gray-700': true }">
        GALLERI
      </router-link>
      <router-link to="/oevrige-vaerker" class="font-bold" :class="{ 'text-[#8baaac]': isActive('/oevrige-vaerker'), 'hover:text-gray-700': true }">
        ØVRIGE VÆRKER
      </router-link>
      <router-link to="/om-mig" class="font-bold" :class="{ 'text-[#8baaac]': isActive('/om-mig'), 'hover:text-gray-700': true }">
        OM MIG
      </router-link>
    </div>

    <span class="w-full h-[2px] bg-black mt-2 block"></span>
  </nav>
</template>

<style scoped></style>
