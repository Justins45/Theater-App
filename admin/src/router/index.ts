import { createRouter, createWebHistory } from "vue-router";
import { routes, handleHotUpdate } from "vue-router/auto-routes";
import { useLoggedInStore } from "@/stores/loggedIn";
import apiClient from '../api/axios'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
});

let sessionCheck: Promise<void> | null = null;

router.beforeEach(async (to) => {
  const loggedInStore = useLoggedInStore();

  sessionCheck ??= apiClient
    .get("/staff/me")
    .then(() => {
      loggedInStore.loggedIn = true;
    })
    .catch(() => {
      loggedInStore.loggedIn = false;
    });

  await sessionCheck;

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