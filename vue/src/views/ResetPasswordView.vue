<script lang="ts" setup>
import {onMounted, ref} from 'vue';
import axios from 'axios';
import FormComponent from '../components/FormComponent.vue';
import BasicInputComponent from '../components/BasicInputComponent.vue';
import FormButtonComponent from '../components/FormButtonComponent.vue';
import TextWithLinkComponent from '../components/TextWithLinkComponent.vue';
import {useRouter} from "vue-router";

const currentEmail = ref('');
const newPassword = ref('');
const repeatedPassword = ref('');
const errorMessage = ref('');
const loading = ref(false);
const router = useRouter();

const isLoggedIn = ref(false);

onMounted(() => {
  const status = sessionStorage.getItem('loggedIn');
  isLoggedIn.value = status === 'true';
});


//Handle form submission
const handleSubmit = async (event: Event) => {
  event.preventDefault();
  errorMessage.value = ''; //Clear any previous error message

  if (!currentEmail.value || !newPassword.value || !repeatedPassword.value) {
    errorMessage.value = 'Email og begge kodeord er påkrævet!';
    return;
  }

  loading.value = true;
  try {
    const response = await axios.post('http://localhost:8080/api/reset-password', {
      currentEmail: currentEmail.value,
      newPassword: newPassword.value,
      repeatedPassword: repeatedPassword.value,
    });


    if (response.data.status === 'success') {
      sessionStorage.removeItem('loggedIn');
      await router.push({ name: 'login' });
    } else {
      errorMessage.value = response.data.message || 'Nulstilling af kodeord mislykkedes!';
    }
  } catch (error: any) {
    if (error.response && error.response.data && error.response.data.message) {
      errorMessage.value = error.response.data.message;
    } else {
      errorMessage.value = 'Noget gik galt under nulstilling af kodeord!';
    }
  } finally {
    loading.value = false; // Reset loading state
  }
};
</script>

<template>
  <div id="app">
    <!-- Form Component with Title -->
    <FormComponent title="Nulstil kodeord" @submit="handleSubmit" formWidth="w-96">

      <!-- Email Input -->
      <BasicInputComponent
          label="EMAIL*"
          name="currentEmail"
          v-model="currentEmail"
          type="email"
          placeholder="mail@mail.com"
          required
          width="w-80"
      ></BasicInputComponent>

      <!-- Password Input -->
      <BasicInputComponent
          label="NYT KODEORD*"
          name="newPassword"
          v-model="newPassword"
          type="password"
          placeholder="*********"
          required
          width="w-80"
      ></BasicInputComponent>

      <!-- Password Input -->
      <BasicInputComponent
          label="GENTAG NYT KODEORD*"
          name="repeatedPassword"
          v-model="repeatedPassword"
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
      <FormButtonComponent :loading="loading">Gem nyt kodeord</FormButtonComponent>

    </FormComponent>
  </div>
</template>

<style scoped>
/* Your styles go here */
</style>
