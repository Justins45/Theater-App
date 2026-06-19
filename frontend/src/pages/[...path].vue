<script setup lang="ts">
import { useRoute } from 'vue-router'
const route = useRoute()
const originalPath = history.state.originalPath ?? route.path
</script>

<template>
  <div>
    <h1>404 - Page Not Found</h1>
    <p v-if="originalPath">Could not find: {{ originalPath }}</p>
  </div>
</template>

<style scoped lang="scss">

</style>
