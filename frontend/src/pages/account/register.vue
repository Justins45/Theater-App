<script setup lang="ts">
import { reactive, ref } from 'vue';
import apiClient from '@/api/axios'

const registerInfo = reactive({
  email: '',
  password: '',
})

const responseMessage = ref("")

async function handleRegister() {
  try {
    const res = await apiClient.post("/auth/register", registerInfo)

    responseMessage.value = `Successfully registered for ${res.data.message}`
  } catch (error: any) {
    console.log(error)
    responseMessage.value = `Error: ${error.message}`;
  }
}
</script>

<template>
  <div>
    <h1>Please Register</h1>
    <form @submit.prevent="handleRegister">
      <label for="email">Email</label>
      <input
        id="email"
        name="email"
        type="email"
        v-model="registerInfo.email"
        placeholder="email@example.ca"
        autocomplete="off"
        required
      />
      <input
        id="password"
        name="password"
        type="password"
        v-model="registerInfo.password"
        placeholder="password"
        autocomplete="off"
        required
      />
      <button type="submit">Register</button>
    </form>
    <p v-if="responseMessage">{{ responseMessage }}</p>
  </div>
</template>

<style scoped lang="scss">

</style>
