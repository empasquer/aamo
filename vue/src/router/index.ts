import { createRouter, createWebHistory } from "vue-router";
import AboutMeView from "../views/AboutMeView.vue";
import Test from "../views/Test.vue";
import HomePage from "../views/HomePage.vue";

// Uncomment and add paths to other components when they are available
// import Gallery from '../views/GalleryView.vue';
// import LogIn from '../views/LogInView.vue';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL), // Add the missing history property
  routes: [
    {
      path: "/",
      name: "home-page",
      component: HomePage,
    },
    {
      path: "/om-mig",
      name: "about-me",
      component: AboutMeView,
    },
    {
      path: "/test",
      name: "test",
      component: Test,
    },
  ],
});

export default router;
