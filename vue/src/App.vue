<script setup lang="ts">
import axios from "axios";
import HeaderComponent from "./components/HeaderComponent.vue";
import FooterComponent from "./components/FooterComponent.vue";
import ContactFixedButtonComponent from "./components/ContactFixedButtonComponent.vue";
</script>

<template>
  <ContactFixedButtonComponent mail-address="Eva.aamo.ea@gmail.com" />
  <HeaderComponent></HeaderComponent>
  <RouterView class="pt-[calc(93px+2rem)]" />
  <FooterComponent></FooterComponent>
</template>

<style scoped></style>
