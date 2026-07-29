import { ref } from "vue";
import { defineStore } from "pinia";
import apiClient from '@/api/axios'

export const useLoggedInStore = defineStore("loggedIn", () => {
  const loggedIn = ref<boolean>(false);

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

  async function logOut() {
    loggedIn.value = false;
    // TODO: make call to server to kill the current cookies (main and refresh)
    // NOTE: function might exist on the logged out page not in here
  }


  return { loggedIn, checkLoggedIn, logOut };
});
