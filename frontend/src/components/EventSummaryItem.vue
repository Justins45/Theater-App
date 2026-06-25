<script setup lang="ts">
import { RouterLink } from "vue-router";
defineProps({
    id: String,
    title: String,
    stageName: String,
})
</script>

<template>
  <div class="event">
    <RouterLink :to="'/events/' + id">
      <h3>{{ title }}</h3>
      <p>Playing at {{ stageName }}</p>
    </RouterLink>
  </div>
</template>

<style scoped lang="scss">
h3, p {
  margin: 0;
  padding: 0;
}
a {
  color: black;
  text-decoration: none;
}
</style>
