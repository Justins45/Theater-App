<script setup lang="ts"></script>

<template>
    <nav>
      <RouterLink to="/">Go to Home View</RouterLink>
      <RouterLink to="/event">Go to Event View</RouterLink>
      <RouterLink to="/patron">Go to Patron View</RouterLink>
    </nav>
    <main>
      <RouterView />
    </main>
</template>

<style scoped></style>
