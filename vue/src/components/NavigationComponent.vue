<script setup lang="ts">
import { useRouter } from "vue-router";

const router = useRouter();

const isActive = (path: string, hash?: string) => {
  const currentRoute = router.currentRoute.value;
  if (hash) {
    return currentRoute.path === path && currentRoute.hash === hash;
  }
  return currentRoute.path === path && !currentRoute.hash;
};
</script>
<template>
  <nav class="flex flex-col justify-end z-50">
    <div class="flex flex-grow justify-end space-x-8 md:space-x-24 px-4 md:px-8">
      <!-- Highlight only when on `/om-mig` with `#events` -->
      <router-link
        :to="{ path: '/om-mig', hash: '#events' }"
        class="font-bold"
        :class="{ 'text-[#4289a3]': isActive('/om-mig', '#events'), 'hover:text-gray-700': true }"
      >
        EVENTS
      </router-link>
      <router-link to="/galleri" class="font-bold" :class="{ 'text-[#4289a3]': isActive('/galleri'), 'hover:text-gray-700': true }">
        GALLERI
      </router-link>
      <router-link to="/oevrige-vaerker" class="font-bold" :class="{ 'text-[#4289a3]': isActive('/oevrige-vaerker'), 'hover:text-gray-700': true }">
        ØVRIGE VÆRKER
      </router-link>
      <!-- Highlight only when on `/om-mig` without `#events` -->
      <router-link
        :to="{ path: '/om-mig' }"
        class="font-bold"
        :class="{ 'text-[#4289a3]': isActive('/om-mig') && !isActive('/om-mig', '#events'), 'hover:text-gray-700': true }"
      >
        OM MIG
      </router-link>
    </div>

    <span class="w-full h-[2px] bg-black mt-2 block"></span>
  </nav>
</template>
