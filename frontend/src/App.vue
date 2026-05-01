<script setup lang="ts">
import { SharedButton } from '@theater/shared'
</script>

<template>
  <h1>You did it!</h1>
  <p>
    Visit <a href="https://vuejs.org/" target="_blank" rel="noopener">vuejs.org</a> to read the
    documentation
  </p>
  <SharedButton />
</template>

<style scoped lang="scss">
p {
  color: $primary-colour;
}
</style>
