import { createRouter, createWebHistory } from "vue-router";
import AboutMeView from "../views/AboutMeView.vue";
import Test from "../views/Test.vue";
import GalleriView from '../views/GalleriView.vue';
// Uncomment and add paths to other components when they are available
// import LogIn from '../views/LogInView.vue';

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL), // Add the missing history property
    routes: [

        {
            path: '/om-mig',
            name: 'about-me',
            component: AboutMeView, // Ensure this is a valid component
        },

        {
            path: '/galleri',
            name: 'galleri',
            component: GalleriView, // Ensure this is a valid component
        },

        {
            path: "/test",
            name: "test",
            component: Test, // Ensure this is a valid component
        },
    ],
});

export default router;
