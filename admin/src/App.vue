<script setup lang="ts">
import NewEventForm from './components/NewEventForm.vue';
import { SharedButton } from '@theater/shared'

// async function greet() {
//   // Learn more about Tauri commands at https://tauri.app/develop/calling-rust/
//   greetMsg.value = await invoke("greet", { name: name.value });
// }
</script>

<template>
  <main class="container">
    <h1>Welcome to Tauri + Vue</h1>
    <NewEventForm />
    <SharedButton />
  </main>
</template>

<style scoped lang="scss">
</style>