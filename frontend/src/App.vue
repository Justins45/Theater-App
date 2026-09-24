<script setup lang="ts">
import { onMounted } from 'vue'
import { RouterLink, RouterView } from "vue-router";
import { useCartStore } from '@stores/cart'
import { useLoggedInStore } from '@stores/loggedIn'

const cartStore = useCartStore()
const loggedInStore = useLoggedInStore()

onMounted(() => {
  if (loggedInStore.justLoggedOut) {
    loggedInStore.justLoggedOut = false
    alert("User Logged Outs")
  }
  loggedInStore.checkLoggedIn()
  cartStore.loadCart()
})

</script>

<template>
  <nav>
    <ul>
      <li><RouterLink to="/">Events</RouterLink></li>
      <template v-if="loggedInStore.loggedIn">
        <li><RouterLink to="/account/me">My Account</RouterLink></li>
        <li><RouterLink to="/cart">Cart<span v-if="cartStore.totalItems > 0"> - {{ cartStore.totalItems }}</span></RouterLink></li>
        <li><RouterLink to="/account/logout">Logout</RouterLink></li>
      </template>
      <template v-else>
        <li><RouterLink to="/account/login">Login</RouterLink></li>
        <li><RouterLink to="/account/register">Register</RouterLink></li>
      </template>

    </ul>

  </nav>
  <router-view></router-view>
</template>

<style scoped lang="scss">
ul {
  list-style: none;
  margin-left: 0;
  padding-left: 0;
  display: flex;
  li {
    margin-right: 1rem;
    a {
      color: black;
      text-decoration: none;
    }
    a:hover {
      text-decoration: underline;
      color: blue;
    }
  }
}
p {
  color: $primary-colour;
}
</style>
