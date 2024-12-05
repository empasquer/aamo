<script setup lang="ts">
import axios from "axios";


async function fetchKommuner() {
  const response = await axios.get("http://localhost:8080/kommuner?regionskode=1084");
  console.log(response);
}
</script>

<template>

  </template>

<style scoped></style>
