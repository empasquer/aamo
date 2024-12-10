<script setup lang="ts">
import { ref } from "vue";
import { useRouter } from "vue-router";

const isOpen = ref(false);
const router = useRouter();

const toggleMenu = () => {
  isOpen.value = !isOpen.value;
};

const closeMenu = () => {
  isOpen.value = false;
};

// Enhanced isActive function
const isActive = (path: string, hash?: string) => {
  const currentRoute = router.currentRoute.value;
  if (hash) {
    return currentRoute.path === path && currentRoute.hash === hash;
  }
  return currentRoute.path === path && !currentRoute.hash;
};
</script>

<template>
  <div>
    <!-- CLOSED BURGER -->
    <button @click="toggleMenu" class="flex justify-center items-center p-0 text-[#4a4a4a] bg-transparent" aria-label="Toggle menu">
      <i v-if="!isOpen" class="fas fa-bars text-2xl"></i>
      <i v-else class="fas fa-times text-2xl"></i>
    </button>

    <!-- OPEN BURGER -->
    <div v-if="isOpen" class="fixed inset-0 bg-transparent z-50 flex flex-col items-center" @click.self="closeMenu">
      <nav
        class="absolute bottom-0 bg-[#000000dc] w-screen h-[calc(100vh-93px)] max-w-md mt-[93px] p-0 flex flex-col items-center space-y-4 justify-around"
        style="animation: fadeIn 0.3s ease-out"
      >
        <router-link
          :to="{ path: '/om-mig', hash: '#events' }"
          class="text-[#EAEAEA] font-bold underline underline-offset-8"
          :class="{ 'text-[#4289a3]': isActive('/om-mig', '#events') }"
          @click="closeMenu"
        >
          EVENTS
        </router-link>
        <router-link
          to="/galleri"
          class="text-[#EAEAEA] font-bold underline underline-offset-8"
          :class="{ 'text-[#4289a3]': isActive('/galleri') }"
          @click="closeMenu"
        >
          GALLERI
        </router-link>
        <router-link
          to="/oevrige-vaerker"
          class="text-[#EAEAEA] underline font-bold underline-offset-8"
          :class="{ 'text-[#4289a3]': isActive('/oevrige-vaerker') }"
          @click="closeMenu"
        >
          ØVRIGE VÆRKER
        </router-link>
        <router-link
          to="/om-mig"
          class="text-[#EAEAEA] font-bold underline underline-offset-8"
          :class="{ 'text-[#4289a3]': isActive('/om-mig') && !isActive('/om-mig', '#events') }"
          @click="closeMenu"
        >
          OM MIG
        </router-link>
      </nav>
    </div>
  </div>
</template>

<style scoped>
@keyframes fadeIn {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}
</style>
