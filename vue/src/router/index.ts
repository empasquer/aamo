import { createRouter, createWebHistory } from "vue-router";
import AboutMeView from "../views/AboutMeView.vue";
import Test from "../views/Test.vue";
import LoginView from "../views/LoginView.vue";
import axios from "axios";

// Uncomment and add paths to other components when they are available
// import Gallery from '../views/GalleryView.vue';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL), // Add the missing history property
  routes: [
    {
      path: "/om-mig",
      name: "about-me",
      component: AboutMeView, // Ensure this is a valid component
    },
    {
      path: "/test",
      name: "test",
      component: Test, // Ensure this is a valid component
    },
    {
      path: "/login",
      name: "login",
      component: LoginView,
    },
  ],
});

// Global navigation guard
router.beforeEach(async (to, from, next) => {
  try {
    const response = await axios.get("http://localhost:8080/api/session-status"); // Check login status with the Spring Boot API
    const isLoggedIn = response.data.loggedIn;

    // If the route contains "/admin/" and the user is not logged in, redirect to log in
    if (to.path.includes("/admin/") && !isLoggedIn) {
      next({ name: "login" }); // Redirect to login page
    } else {
      next(); // Allow the navigation
    }
  } catch (error) {
    console.error("Error checking login status:", error);
    next({ name: "login" }); // Proceed even if the API fails...just redirects to login for now
  }
});

export default router;
