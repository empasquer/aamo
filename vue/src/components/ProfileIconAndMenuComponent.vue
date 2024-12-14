<script setup lang="ts">

import {ref} from "vue";
import FormComponent from "./FormComponent.vue";
import BodyTextComponent from "./BodyTextComponent.vue";
const showMenu =ref(false)
const showAddArtwork= ref(false);
const toggleMenu = () =>{
  showMenu.value = !showMenu.value;
}
const addArtwork = () => {
  showMenu.value = false;
  showAddArtwork.value = true;
}
</script>

<template>
  <!-- Display modals here -->
  <div v-if="showAddArtwork" class="absolute z-10 top-1">
    <FormComponent><button @click="showAddArtwork=false">kryds</button></FormComponent>

  </div>
  <div class="profile-container "
       @mouseenter="showMenu = true"
       @mouseleave="showMenu = false">
    <div class="profile-icon fixed z-10 p-2 left-10 bottom-2  " @click="toggleMenu">

      <i class="fa-solid fa-user fa-2x "></i>
    </div>
  <div v-if="showMenu" class="profile-menu fixed  left-2 bottom-14 bg-[#4a4a4a] opacity-90  w-28 h-auto text-white"
  >
      <BodyTextComponent class="t underline cursor-pointer "textContent="Tilføj Kunstværk" @click="addArtwork"></BodyTextComponent>


  </div>

  </div>
</template>

<style scoped>
.t {
  font-size: medium;
  color: #f9f9f9;
}

</style>