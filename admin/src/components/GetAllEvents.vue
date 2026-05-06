<script setup lang="ts">
import { ref } from 'vue'
import { fetch } from "@tauri-apps/plugin-http"

const BASE_URL = import.meta.env.VITE_API_URL

const data = ref()

async function getAllEvents() {
  try {
    const res = await fetch(`${BASE_URL}/events`, {
      method: "GET",
      headers: {
        "Content-Type": 'application/json'
      }
    });
  data.value = await res.json()
  } catch (error) {
    console.error("fetch error ", error)
  }
   
}
</script>
<template>
  <h2>Get all Events</h2>
  <button @click="getAllEvents()">Click me - Events</button>
  <pre>{{ data }}</pre>
</template>
<style scoped lang="scss">
  h2 {
    font-size: 1.2rem;
  }
  pre {
    flex: 1;
    min-width: 0;
    white-space: pre-wrap;
  }
</style>