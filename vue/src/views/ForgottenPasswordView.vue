<script lang="ts" setup>
import { ref } from 'vue';
import axios from 'axios';
import FormComponent from '../components/FormComponent.vue';
import BasicInputComponent from '../components/BasicInputComponent.vue';
import FormButtonComponent from '../components/FormButtonComponent.vue';
import TextWithLinkComponent from '../components/TextWithLinkComponent.vue';
import {useRouter} from "vue-router";

const email = ref('');
const errorMessage = ref('');
const loading = ref(false);
const router = useRouter();

//Handle form submission
const handleSubmit = async (event: Event) => {
  event.preventDefault();
  errorMessage.value = ''; //Clear any previous error message

  if (!email.value) {
    errorMessage.value = 'Email er påkrævet!';
    return;
  }

  loading.value = true;
  try {
    const response = await axios.post('http://localhost:8080/api/forgotten-password', {
      email: email.value,
    });

    if (response.data.status === 'success') {
      await router.push({ name: 'login' });
    } else {
      errorMessage.value = response.data.message || 'Mislykkedes at sende email!';
    }
  } catch (error: any) {
    if (error.response && error.response.data && error.response.data.message) {
      errorMessage.value = error.response.data.message;
    } else {
      errorMessage.value = 'Noget gik galt!';
    }
  } finally {
    loading.value = false; // Reset loading state
  }
};
</script>

<template>
  <div id="app">
    <!-- Form Component with Title -->
    <FormComponent title="Glemt kodeord" @submit="handleSubmit" formWidth="w-96">

      <!-- Email Input -->
      <BasicInputComponent
          label="DIN KONTO EMAIL*"
          name="email"
          v-model="email"
          type="email"
          placeholder="mail@mail.com"
          required
          width="w-80"
      ></BasicInputComponent>

      <!-- Error Message -->
      <div v-if="errorMessage">
        <p class="text-red-700 text-base font-medium mb-3 underline">{{ errorMessage }}</p>
      </div>

      <!-- Submit Button -->
      <FormButtonComponent :loading="loading">Send mail</FormButtonComponent>

      <TextWithLinkComponent
          text="Har du husket dit kodeord alligevel? Klik her for at gå tilbage til log ind"
          link="login"
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
