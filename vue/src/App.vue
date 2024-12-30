<script setup lang="ts">
import axios from "axios";
import { useRoute } from "vue-router";
import HeaderComponent from "./components/HeaderComponent.vue";
import FooterComponent from "./components/FooterComponent.vue";
import ContactFixedButtonComponent from "./components/ContactFixedButtonComponent.vue";
import ProfileIcon from './components/ProfileIconAndMenuComponent.vue'
import {computed} from "vue";

const route = useRoute();
const isAdminRoute = computed(()=> route.path.startsWith("/admin"));

</script>

<template>
  <ContactFixedButtonComponent mail-address="Eva.aamo.ea@gmail.com" />
  <HeaderComponent></HeaderComponent>
  <RouterView class="pt-[calc(93px+2rem)]" :key="$route.fullPath" />
  <ProfileIcon v-if="isAdminRoute" />
  <FooterComponent></FooterComponent>
</template>

<style scoped></style>
