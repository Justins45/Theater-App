import { ref } from "vue";
import { defineStore } from "pinia";
import apiClient from '@/api/axios'
import router from "@/router";

export const useLoggedInStore = defineStore("loggedIn", () => {
  const loggedIn = ref<boolean>(false);
  const justLoggedOut = ref<boolean>(false);

  // make call to API /me
  async function checkLoggedIn() {
    try {
      const res = await apiClient.get('/auth/me');
      if (res.status === 200) {
        console.log("User is logged in");
        loggedIn.value = true;
      }

    } catch (e) {
      console.log("User is NOT logged in");
      loggedIn.value = false;
    }
  }

  // TODO: lock down website endpoints when not logged in
  // redirects on those pages
  // dont remember much from here even though i just coded it lol

  async function logOut() {
    loggedIn.value = false;
    justLoggedOut.value = true;
    // TODO: make call to server to kill the current cookies (main and refresh)
    // redirect to home page
    await router.push("/");
  }


  return { loggedIn, checkLoggedIn, logOut, justLoggedOut };
});
