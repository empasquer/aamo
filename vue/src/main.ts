import { createApp } from "vue";
import "./style.css";
import App from "./App.vue";
import router from "./router";
import VueTouch from 'vue3-touch-events';

// Create the Vue app and mount it with the router
createApp(App)
  .use(VueTouch) //register before router or it can cause problems
  .use(router)
  .mount("#app");
