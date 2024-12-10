import { createRouter, createWebHistory } from "vue-router";
import AboutMeView from "../views/AboutMeView.vue";
import GalleriView from "../views/GalleriView.vue";
import OtherArtworksView from "../views/OtherArtworksView.vue";
import HomePage from "../views/HomePage.vue";

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

export default router;
