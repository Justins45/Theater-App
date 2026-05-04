<script setup lang="ts">
import { ref } from "vue";
import { invoke } from "@tauri-apps/api/core";
import { SharedButton } from '@theater/shared'

// async function greet() {
//   // Learn more about Tauri commands at https://tauri.app/develop/calling-rust/
//   greetMsg.value = await invoke("greet", { name: name.value });
// }

const createEventMSG = ref("")

interface FormProps {
  title: string,
  description: string,
  director: string,
  capacity: number | null,
}

const form = ref<FormProps>({
  title: "",
  description: "",
  director: "",
  capacity: null,
})
// Vue call off to Rust to take and make the JSON for a new event while checking each field exists and is valid (form injection prevention)
async function create_event() {
  createEventMSG.value = await invoke("create_event_command", {title: form.value.title, description: form.value.description, director: form.value.director, capacity: form.value.capacity})
}

</script>

<template>
  <main class="container">
    <h1>Welcome to Tauri + Vue</h1>

    <div class="form">
      <h2>Create New Event</h2>
      <form class="row" @submit.prevent="create_event">
        <input id="title-input" v-model="form.title" placeholder="Enter a title..." type="text" />
        <input id="description-input" v-model="form.description" placeholder="Event description..." type="text" />
        <input id="director-input" v-model="form.director" placeholder="Directors name..." type="text" />
        <input id="capacity-input" v-model.number="form.capacity" placeholder="Event capacity...." type="number" />
        <button type="submit">Submit</button>
      </form>
      <p>{{ form }}</p>
    </div>
    <p>Return from Rust: {{ createEventMSG }}</p>


    <SharedButton />
  </main>
</template>

<style scoped lang="scss">
.form {
  h2 {
    font-size: 1.2rem;
  }
  form {
    display: flex;
    flex-direction: column;
    width: 20rem;
    justify-content: space-between;
    height: 12rem;
  }
}
</style>