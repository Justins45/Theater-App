<script setup lang="ts">
import { invoke } from '@tauri-apps/api/core'
import { ref } from 'vue'

const createPatronMSG = ref('')

interface FormProps {
  firstName: string,
  lastName: string,
  email: string
}

const defaultData = () => ({
  firstName: "",
  lastName: "",
  email: ""
})

const form = ref<FormProps>(defaultData())

async function create_patron() {
createPatronMSG.value = await invoke("create_patron_command", 
  {
    firstName: form.value.firstName,
    lastName: form.value.lastName,
    email: form.value.email
  })

  form.value = defaultData()
}
</script>
<template>
  <div>
    <h2>Create new Patron</h2>
    <form @submit.prevent="create_patron">
      <input type="text" v-model="form.firstName" placeholder="First name...">
      <input type="text" v-model="form.lastName" placeholder="Last name...">
      <input type="text" v-model="form.email" placeholder="Patron email...">
      <button type="submit">Submit</button>
    </form>
    <p>{{ form }}</p>
  </div>
  <p>Creation Message: {{ createPatronMSG }}</p>
</template>
<style lang="scss" scoped>
h2 {
  font-size: 1.2rem;
}
form {
  display: flex;
  flex-direction: column;
  width: 20rem;
  height: 8rem;
  justify-content: space-between;
}
</style>