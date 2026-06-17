<script setup lang="ts">
import { reactive, ref } from 'vue';
import apiClient from '@/api/axios'

const loginInfo = reactive({
  email: '',
  password: '',
})

const responseMessage = ref("")

async function handleLogin() {
  try {
    const res = await apiClient.post("/auth/login", loginInfo)

    responseMessage.value = `Successfully logged in ${res.data.id}`
  } catch (error: any) {
    console.log(error)
    responseMessage.value = `Error: ${error.message}`;
  }
}
</script>

<template>
<div>
  <h1>Please login</h1>
  <form @submit.prevent="handleLogin">
    <label for="email">Email</label>
    <input
      id="email"
      name="email"
      type="email"
      v-model="loginInfo.email"
      placeholder="email@example.ca"
      autocomplete="email"
      required
    />
    <input
      id="password"
      name="password"
      type="password"
      v-model="loginInfo.password"
      placeholder="password"
      autocomplete="current-password"
      required
    />
    <button type="submit">Login</button>
  </form>
  <p v-if="responseMessage">{{ responseMessage }}</p>
</div>
</template>

<style scoped lang="scss">

</style>
