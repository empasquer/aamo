<script setup lang="ts">
function handleClick() {
  window.location.href = "/";
}

const props = defineProps<{
  width?: string;
  height?: string;
  fill?: string;
  opacity?: string;
}>();

// Compute the fill opacity based on the `opacity` prop
const computedOpacity = props.opacity == "true" ? 0.75 : 1;
</script>

<template>
  <button @click="handleClick" class="max-w-[100vw] bg-transparent border-0 p-0 flex items-center justify-center">
    <svg class="aspect-[1048/201]" :class="[width, height]" viewBox="0 0 1048 201" fill="none" xmlns="http://www.w3.org/2000/svg">
      <path
        d="M0 198.325V2.67491H117.898V23.692H23.656V89.8004H111.794V110.817H23.656V177.308H119.425V198.325H0Z"
        :fill="fill || 'white'"
        :fill-opacity="computedOpacity"
      />
      <path
        d="M130.944 2.67491L188.939 167.373H191.228L249.224 2.67491H274.024L202.293 198.325H177.874L106.143 2.67491H130.944Z"
        :fill="fill || 'white'"
        :fill-opacity="computedOpacity"
      />
      <path
        d="M258.072 198.325H233.271L305.002 2.67491H329.421L401.152 198.325H376.352L317.975 33.6274H316.449L258.072 198.325Z"
        :fill="fill || 'white'"
        :fill-opacity="computedOpacity"
      />
      <path
        d="M401.988 198.325H377.187L448.918 2.67491H473.338L545.069 198.325H520.268L461.891 33.6274H460.365L401.988 198.325Z"
        :fill="fill || 'white'"
        :fill-opacity="computedOpacity"
      />
      <path
        d="M545.904 198.325H521.104L592.835 2.67491H617.254L688.985 198.325H664.184L605.807 33.6274H604.281L545.904 198.325Z"
        :fill="fill || 'white'"
        :fill-opacity="computedOpacity"
      />
      <path
        d="M681.808 2.67491H710.043L776.432 165.08H778.721L845.111 2.67491H873.345V198.325H851.215V49.6768H849.308L788.26 198.325H766.893L705.845 49.6768H703.938V198.325H681.808V2.67491Z"
        :fill="fill || 'white'"
        :fill-opacity="computedOpacity"
      />
      <path
        d="M1048 100.5C1048 121.135 1044.28 138.968 1036.84 153.998C1029.4 169.029 1019.19 180.62 1006.22 188.772C993.248 196.924 978.431 201 961.77 201C945.109 201 930.292 196.924 917.32 188.772C904.347 180.62 894.141 169.029 886.7 153.998C879.26 138.968 875.54 121.135 875.54 100.5C875.54 79.865 879.26 62.0323 886.7 47.0019C894.141 31.9715 904.347 20.3802 917.32 12.2281C930.292 4.07605 945.109 0 961.77 0C978.431 0 993.248 4.07605 1006.22 12.2281C1019.19 20.3802 1029.4 31.9715 1036.84 47.0019C1044.28 62.0323 1048 79.865 1048 100.5ZM1025.11 100.5C1025.11 83.5589 1022.28 69.2609 1016.62 57.606C1011.02 45.951 1003.42 37.1302 993.82 31.1435C984.281 25.1568 973.598 22.1635 961.77 22.1635C949.942 22.1635 939.227 25.1568 929.625 31.1435C920.086 37.1302 912.487 45.951 906.827 57.606C901.231 69.2609 898.433 83.5589 898.433 100.5C898.433 117.441 901.231 131.739 906.827 143.394C912.487 155.049 920.086 163.87 929.625 169.856C939.227 175.843 949.942 178.836 961.77 178.836C973.598 178.836 984.281 175.843 993.82 169.856C1003.42 163.87 1011.02 155.049 1016.62 143.394C1022.28 131.739 1025.11 117.441 1025.11 100.5Z"
        :fill="fill || 'white'"
        :fill-opacity="computedOpacity"
      />
      <path d="M268.038 108H652.066V133.221H268.038V108Z" :fill="fill || 'white'" :fill-opacity="computedOpacity" />
    </svg>
  </button>
</template>
