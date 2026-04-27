<script setup lang="ts"></script>

<template>
  <div id="app">
    <nav>
      <RouterLink to="/">Go to Home View</RouterLink>
      <RouterLink to="/event">Go to Event View</RouterLink>
      <RouterLink to="/patron">Go to Patron View</RouterLink>
    </nav>
    <main>
      <RouterView />
    </main>
  </div>
</template>

<style scoped></style>
