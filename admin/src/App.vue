<script setup lang="ts">
import { RouterView, RouterLink } from 'vue-router';
</script>

<template>


  <nav>
    <ul>
      <li>
        <RouterLink to="/">Home</RouterLink>
      </li>
      <li>
        <Routerlink to="/logout">Logout</Routerlink>
      </li>
    </ul>
    
  </nav>


  <router-view></router-view>  
</template>

<style scoped>
nav ul {
  display: flex;
  flex-direction: row;
  list-style: none;
  margin-left: -2rem;
  li {
    margin-right: 1rem;
  }
}
</style>
