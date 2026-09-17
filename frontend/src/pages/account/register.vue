<script setup lang="ts">
import { reactive, ref } from 'vue';
import apiClient from '@api'


const registerInfo = reactive({
  email: '',
  password: '',
})

const responseMessage = ref("")

async function handleRegister() {
  try {
    const res = await apiClient.post("/auth/register", registerInfo)

    responseMessage.value = `Successfully registered for ${res.data.message}`
  } catch (error) {
    console.log(error)
    responseMessage.value = `Error: ${error}`;
  }
}
</script>

<template>
  <div>
    <h1>Create an Account</h1>
    <div class="register-form">
      <form @submit.prevent="handleRegister">
        <div class="email">
          <label for="email">Email Address</label>
          <input
            id="email"
            name="email"
            type="email"
            v-model="registerInfo.email"
            placeholder="email@example.ca"
            autocomplete="off"
            required
          />
        </div>

        <div class="password">
          <label for="password">Password</label>
          <input
            id="password"
            name="password"
            type="password"
            v-model="registerInfo.password"
            placeholder="password"
            autocomplete="off"
            required
          />
        </div>
        <button type="submit">Register</button>
      </form>
    </div>
    <p v-if="responseMessage">{{ responseMessage }}</p>
  </div>
</template>

<style scoped lang="scss">
// TODO: Ensure proper focous highlighting when tabbing though

.email, .password {
  display: flex;
  flex-direction: column;
  width: 15rem;
  margin-bottom: 1rem;
}
</style>
