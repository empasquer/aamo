<script lang="ts" setup>
import { ref } from 'vue';
import axios from 'axios';
import FormComponent from '../components/FormComponent.vue';
import BasicInputComponent from '../components/BasicInputComponent.vue';
import FormButtonComponent from '../components/FormButtonComponent.vue';
import TextWithLinkComponent from '../components/TextWithLinkComponent.vue';
import {useRouter} from "vue-router";

const email = ref('');
const password = ref('');
const errorMessage = ref('');
const loading = ref(false);
const router = useRouter();


//Handle form submission
const handleSubmit = async (event: Event) => {
  event.preventDefault();
  errorMessage.value = ''; //Clear any previous error message

  if (!email.value || !password.value) {
    errorMessage.value = 'Email og kodeord er påkrævet!';
    return;
  }

  loading.value = true;
  try {
    const response = await axios.post('http://localhost:8080/api/login', {
      email: email.value,
      password: password.value,
    });

    if (response.data.status === 'success') {
      sessionStorage.setItem('loggedIn', 'true');
      await router.push({ name: 'admin-galleri' });
    } else {
      errorMessage.value = response.data.message || 'Log ind mislykkedes!';
    }
  } catch (error: any) {
    if (error.response && error.response.data && error.response.data.message) {
      errorMessage.value = error.response.data.message;
    } else {
      errorMessage.value = 'Noget gik galt under log ind!';
    }
  } finally {
    loading.value = false; // Reset loading state
  }
};
</script>

<template>
  <div id="app">
    <!-- Form Component with Title -->
    <FormComponent title="Log ind" @submit="handleSubmit"  formWidth="w-96">

      <!-- Email Input -->
      <BasicInputComponent
          label="EMAIL*"
          name="email"
          v-model="email"
          type="email"
          placeholder="mail@mail.com"
          required
          width="w-80"
      ></BasicInputComponent>

      <!-- Password Input -->
      <BasicInputComponent
          label="KODEORD*"
          name="password"
          v-model="password"
          type="password"
          placeholder="*********"
          required
          width="w-80"
      ></BasicInputComponent>

      <!-- Error Message -->
      <div v-if="errorMessage">
        <p class="text-red-700 text-base font-medium mb-3 underline">{{ errorMessage }}</p>
      </div>

      <!-- Submit Button -->
      <FormButtonComponent :loading="loading">Log ind</FormButtonComponent>

      <!-- Forgot Password Link -->
      <TextWithLinkComponent
          text="Har du glemt dit kodeord? Klik her for at få tilsendt et nyt."
          link="glemt-kodeord"
          font-size="text-sm"
          text-align="text-center"
          padding="pt-4"
          font-weight="font-medium"
      ></TextWithLinkComponent>
    </FormComponent>
  </div>
</template>

<style scoped>
/* Your styles go here */
</style>
