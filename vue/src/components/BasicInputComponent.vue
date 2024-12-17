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
  },
  width: {
    type:String,
    default: 'w-24'
  }
});

// Define emits for two-way data binding
const emit = defineEmits(['update:modelValue']);

// Method to handle input change and emit the new value
const handleInput = (event: Event) => {
  const input = event.target as HTMLInputElement;
  if (props.type === 'number') {
    emit('update:modelValue', Number(input.value));
  } else {
    emit('update:modelValue', input.value);
  }
  emit('update:modelValue', input.value);
};
</script>


<template>
  <div class="mb-5">
    <label :for="props.label" class="text-[#EAEAEA] block text-base font-medium mb-1.5 text-left ">{{ props.label }}</label>
    <component
        :is="props.type === 'textArea' ? 'textarea' : 'input'"
        :type="props.type"
        :id="props.name"
        :placeholder="props.placeholder"
        :name="props.name"
        :value="props.modelValue"
        @input="handleInput"
        :class="props.width"
        class=" p-2 border focus:outline-none focus:ring-2 focus:ring-gray-400"
        :rows="props.type === 'textArea' ? 4 : undefined"
    />
  </div>
</template>

<style scoped>
/* For Webkit browsers (Chrome, Safari, Edge, etc.) */
input[type="number"]::-webkit-outer-spin-button,
input[type="number"]::-webkit-inner-spin-button {
  -webkit-appearance: none;
  margin: 0;
}

/* For Firefox */
input[type="number"] {
  -moz-appearance: textfield;
}
/* Adjust the size of checkboxes without scaling the label */
input[type="checkbox"] {
  transform: scale(2); /* Adjust checkbox size */
  vertical-align: middle;
  focus:ring-0;
  outline: none;
}
</style>
