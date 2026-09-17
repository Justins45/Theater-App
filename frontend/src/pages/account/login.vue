<script setup lang="ts">
import { reactive, ref } from 'vue';
import apiClient from '@api'
import router from '@/router'

const loginInfo = reactive({
  email: '',
  password: '',
})

const responseMessage = ref("")

async function handleLogin() {
  try {
    const res = await apiClient.post("/auth/login", loginInfo)

    responseMessage.value = `Successfully logged in ${res.data.message}`
    await router.push('/');
    window.location.reload();
  } catch (error) {
    console.log(error)
    responseMessage.value = `Error: ${error}`;
  }
}
</script>

<template>
<div>
  <h1>Please login</h1>
  <div class="login-form">
    <form @submit.prevent="handleLogin">
      <div class="email">
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
      </div>
      <div class="password">
        <label for="password">Password</label>
        <input
          id="password"
          name="password"
          type="password"
          v-model="loginInfo.password"
          placeholder="password"
          autocomplete="current-password"
          required
        />
      </div>
      <button type="submit">Login</button>
    </form>
  </div>
  <p v-if="responseMessage">{{ responseMessage }}</p>
</div>
</template>

<style scoped lang="scss">

.email, .password {
  display: flex;
  flex-direction: column;
  width: 15rem;
  margin-bottom: 1rem;
}
</style>
