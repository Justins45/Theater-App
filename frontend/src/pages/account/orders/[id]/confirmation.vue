<script lang="ts" setup>
import { ref } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()

const routeId = ref(route.params.id)

// NOTE: Used for order confirmation - NOT for viewing orders from account (maybe)

</script>
<template>
  <div>
    <p>Order Confirmation - {{ routeId }}</p>
  </div>
</template>
<style lang="scss" scoped>

</style>

