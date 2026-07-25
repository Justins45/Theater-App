<script setup lang="ts">
import { SharedButton } from "@theater/shared";
import { RouterLink, RouterView } from "vue-router";
import ShoppingCart from '@/components/ShoppingCart.vue'
import { useCartStore } from '@/stores/cart'

const cartStore = useCartStore()
cartStore.loadCart()

</script>

<template>
  <nav>
    <ul>
      <li><RouterLink to="/">Home</RouterLink></li>
      <li><RouterLink to="/events">events</RouterLink></li>
      <li><RouterLink to="/account/login">Login</RouterLink></li>
      <li><RouterLink to="/account/register">Register</RouterLink></li>
      <li><RouterLink to="/account/me">My Account</RouterLink></li>
      <li><RouterLink to="/cart">Cart<span v-if="cartStore.totalItems > 0"> - {{ cartStore.totalItems }}</span></RouterLink></li>
    </ul>

  </nav>
  <SharedButton />
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
      color: red;
      text-decoration: none;
    }
    a:hover {
      text-decoration: underline;
      color: green;
    }
  }
}
p {
  color: $primary-colour;
}
</style>
