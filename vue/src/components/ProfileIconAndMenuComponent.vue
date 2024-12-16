<script setup lang="ts">

import FormComponent from "./FormComponent.vue";
import BodyTextComponent from "./BodyTextComponent.vue";
import axios from "axios";
import router from "../router";
import {ref} from "vue";
const showMenu =ref(false)
const showAddArtwork= ref(false);
const toggleMenu = () =>{
  showMenu.value = !showMenu.value;
}
const addArtwork = () => {
  showMenu.value = false;
  showAddArtwork.value = true;
}

const logout = async () => {
  try {
    await axios.post(`http://localhost:8080/api/logout`);
    sessionStorage.removeItem('loggedIn');
    await router.push({ name: 'homepage' });
  } catch (error) {
    console.error("Error logging out: ", error);
  }
};
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
      <BodyTextComponent class="t underline cursor-pointer font-bold" textContent="Tilføj Kunstværk" @click="addArtwork"></BodyTextComponent>
      <BodyTextComponent class="t underline cursor-pointer font-bold" textContent="Log ud" @click="logout"></BodyTextComponent>


  </div>

  </div>
</template>

<style scoped>
.t {
  font-size: medium;
  color: #f9f9f9;
}

</style>