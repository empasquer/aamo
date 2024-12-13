import { createRouter, createWebHistory } from "vue-router";
import AboutMeView from "../views/AboutMeView.vue";
import GalleriView from "../views/GalleriView.vue";
import OtherArtworksView from "../views/OtherArtworksView.vue";
import HomePage from "../views/HomePage.vue";
import LoginView from "../views/LoginView.vue";
import ForgottenPasswordView from "../views/ForgottenPasswordView.vue";
import ResetPasswordView from "../views/ResetPasswordView.vue";

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
    {
      path: "/glemt-kodeord",
      name: "forgotten-password",
      component: ForgottenPasswordView,
    },
    {
      path: "/admin/nulstil-kodeord",
      name: "reset-password",
      component: ResetPasswordView,
    },
    {
      path: "/admin/galleri",
      name: "admin-galleri",
      component: GalleriView,
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

//Global navigation guard
router.beforeEach(async (to, from, next) => {
  if (to.path.startsWith("/admin")) {
    try {
      const isLoggedIn = sessionStorage.getItem('loggedIn') === 'true';

      if (!isLoggedIn) {
        // If not logged in, redirect to login page
        next({ name: 'login' });
        return;
      }
    } catch (error) {
      console.error("Error checking login status:", error);
      next({ name: "homepage" });
      return;
    }
  }
  next(); //Allows navigation for all other routes
});

export default router;
