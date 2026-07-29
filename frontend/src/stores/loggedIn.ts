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


  return { loggedIn, checkLoggedIn };
});
