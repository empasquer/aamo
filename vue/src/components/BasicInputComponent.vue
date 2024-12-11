<script setup lang="ts">
import { defineProps, defineEmits } from 'vue';

// Define props for the component
const props = defineProps({
  label: {
    type: String,
    required: true,
  },
  type: {
    type: String,
    default: 'text',
  },
  placeholder: {
    type: String,
    default: '',
  },
  name: {
    type: String,
    required: true,
  },
  modelValue: {
    type: String,
    default: '',
  }
});

// Define emits for two-way data binding
const emit = defineEmits(['update:modelValue']);

// Method to handle input change and emit the new value
const handleInput = (event: Event) => {
  const input = event.target as HTMLInputElement;
  emit('update:modelValue', input.value);
};
</script>


<template>
  <div class="mb-5">
    <label :for="props.label" class="text-[#EAEAEA] block text-base font-medium mb-1.5 text-left">{{ props.label }}</label>
    <input
        :type="props.type"
        :id="props.name"
        :placeholder="props.placeholder"
        :name="props.name"
        :value="props.modelValue"
        @input="handleInput"
        class="w-full p-2 border focus:outline-none focus:ring-2 focus:ring-gray-400"
    />
  </div>
</template>

<style scoped>
/* You can add component-specific styles here */
</style>
