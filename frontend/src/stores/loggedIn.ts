import { ref } from "vue";
import { defineStore } from "pinia";
import apiClient from '@/api/axios'

export const useLoggedInStore = defineStore("loggedIn", () => {
  const loggedIn = ref<boolean>(false);

  // make call to API /me
  async function checkLoggedIn() {
    try {
      const res = apiClient.get("/me");

      loggedIn.value = true;
    } catch (e) {
      loggedIn.value = false;
    }
  }


  return { loggedIn, checkLoggedIn };
});
