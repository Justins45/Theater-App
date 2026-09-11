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
      console.log(`User is NOT logged in :: ${e}`);
      loggedIn.value = false;
    }
  }

  // TODO: lock down website endpoints when not logged in
  // redirects on those pages
  // dont remember much from here even though i just coded it lol

  async function logOut() {
    loggedIn.value = false;
    justLoggedOut.value = true;
    try {
      const res = await apiClient.post('/auth/logout');
      if (res.status = 204) {
        console.log("Killed cookie?")
      }
    } catch (e) {
      console.log(`Loggout did not work :: ${e}`)
      justLoggedOut.value = false
    }
    // redirect to home page
    await router.push("/");
  }


  return { loggedIn, checkLoggedIn, logOut, justLoggedOut };
});
