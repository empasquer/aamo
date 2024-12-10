<script lang="ts" setup>
import { ref } from 'vue';
import axios from 'axios';
import FormComponent from '../components/FormComponent.vue';
import BasicInputComponent from '../components/BasicInputComponent.vue';
import FormButtonComponent from '../components/FormButtonComponent.vue';
import TextWithLinkComponent from '../components/TextWithLinkComponent.vue';
import {useRouter} from "vue-router";

// State for form data and error messages
const email = ref('');
const password = ref('');
const errorMessage = ref('');
const router = useRouter();


//Handle form submission
const handleSubmit = async (event: Event) => {
  event.preventDefault();
  errorMessage.value = ''; //Clear any previous error message

  if (!email.value || !password.value) {
    errorMessage.value = 'Email og kodeord er påkrævet!';
    return;
  }

  try {
    const response = await axios.post('http://localhost:8080/api/login', {
      email: email.value,
      password: password.value,
    });

    console.log('Login Response:', response);

    if (response.data.status === 'success') {
      console.log('Login successful, redirecting to /om-mig');
      await router.push({ name: 'about-me' });
    } else {
      errorMessage.value = response.data.message || 'Log ind mislykkedes!';
      console.error('Login failed:', response.data);
    }
  } catch (error: any) {
    if (error.response && error.response.data && error.response.data.message) {
      errorMessage.value = error.response.data.message;
    } else {
      errorMessage.value = 'Noget gik galt under log ind!';
    }
    console.error('Error during login:', error);
  }
};
</script>

<template>
  <div id="app">
    <!-- Form Component with Title -->
    <FormComponent title="Log ind" @submit="handleSubmit">

      <!-- Email Input -->
      <BasicInputComponent
          label="EMAIL*"
          name="email"
          v-model="email"
          type="email"
          placeholder="mail@mail.com"
          required
      ></BasicInputComponent>

      <!-- Password Input -->
      <BasicInputComponent
          label="KODEORD*"
          name="password"
          v-model="password"
          type="password"
          placeholder="*********"
          required
      ></BasicInputComponent>

      <!-- Error Message -->
      <div v-if="errorMessage" class="text-red-700 text-base font-medium mb-3 underline">
        <p>{{ errorMessage }}</p>
      </div>

      <!-- Submit Button -->
      <FormButtonComponent>Log ind</FormButtonComponent>

      <!-- Forgot Password Link -->
      <TextWithLinkComponent
          text="Har du glemt dit kodeord? Klik her og få tilsendt et nyt."
          link="forgotten-password"
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
