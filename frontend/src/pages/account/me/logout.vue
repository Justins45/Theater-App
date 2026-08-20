<script setup lang="ts">
import { useLoggedInStore} from '@/stores/loggedIn'

const loggedInStore = useLoggedInStore()

</script>

<template>
  <div>
    <button @onClick="loggedInStore.logOut">Log out</button>
  </div>
</template>

<style scoped lang="scss">

</style>
