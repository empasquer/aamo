import { createRouter, createWebHistory } from "vue-router";
import AboutMeView from "../views/AboutMeView.vue";
import GalleriView from "../views/GalleriView.vue";
import OtherArtworksView from "../views/OtherArtworksView.vue";
import HomePage from "../views/HomePage.vue";
import LoginView from "../views/LoginView.vue";
import axios from "axios";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "homepage",
      component: HomePage,
    },
    {
      path: "/om-mig",
      name: "about-me",
      component: AboutMeView,
    },
    {
      path: "/galleri",
      name: "galleri",
      component: GalleriView,
    },
    {
      path: "/oevrige-vaerker",
      name: "other-artworks",
      component: OtherArtworksView,
    },
    {
      path: "/login",
      name: "login",
      component: LoginView,
    },
  ],
  scrollBehavior(to, from, savedPosition) {
    if (to.hash) {
      const headerHeight = document.querySelector("header")?.offsetHeight || 0;
      const viewportHeight = window.innerHeight;

      return {
        el: to.hash,
        top: headerHeight > 0 ? headerHeight : viewportHeight * 0.1,
        behavior: "smooth",
      };
    } else if (savedPosition) {
      return savedPosition;
    } else {
      return { top: 0 };
    }
  },
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
