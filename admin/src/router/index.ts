import { createRouter, createWebHistory } from "vue-router";
import { routes, handleHotUpdate } from "vue-router/auto-routes";
import { useLoggedInStore } from "@/stores/loggedIn";


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
});

router.beforeEach((to) => {
  const loggedInStore = useLoggedInStore();

  if (to.path !== '/login' && !loggedInStore.loggedIn) {
    return {
      path: '/login',
      query: { redirect: to.fullPath }
    }
  }
})

if (import.meta.hot) {
  handleHotUpdate(router);
}

export default router;