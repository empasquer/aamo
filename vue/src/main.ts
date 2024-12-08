import { createApp } from "vue";
import "./style.css";
import App from "./App.vue";
import router from "./router";
import VueTouch from 'vue3-touch-events';

//createApp(App).mount('#app')
//createApp(TestZone).mount("#app");

// Create the Vue app and mount it with the router
createApp(App)
  .use(router) // Use the router
  .use(VueTouch)
  .mount("#app");
