<script setup lang="ts">
import NewEventForm from './components/forms/NewEventForm.vue';
import GetAllEvents from './components/GetAllEvents.vue';
import NewPatronForm from './components/forms/NewPatronForm.vue';
import GetAllPatrons from './components/GetAllPatrons.vue';
import { onMounted } from "vue";
import { getCurrentWindow } from "@tauri-apps/api/window";
import { SharedButton } from '@theater/shared'

// Allow Tauri to show window again after resize is complete
onMounted(async () => {
  await getCurrentWindow().show()
})


// async function greet() {
//   // Learn more about Tauri commands at https://tauri.app/develop/calling-rust/
//   greetMsg.value = await invoke("greet", { name: name.value });
// }
</script>

<template>
  <main>
    <h1>Welcome to Tauri + Vue</h1>
    <div class="container">
      <div>
        <h2>Create Forms</h2>
        <NewEventForm />
        <SharedButton />
        <NewPatronForm />
      </div>
      <div>
        <h2>Get forms</h2>
        <GetAllPatrons />
        <GetAllEvents />
      </div>
    </div>
  
    
  </main>
</template>

<style scoped lang="scss">
.container {
  display: flex;
  flex-direction: row;
  gap: 3rem;
  
  div {
    &:first-child {
      max-width: 30rem;
    }
  }
}
</style>