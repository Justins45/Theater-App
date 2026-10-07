<script lang="ts" setup>
import { ref } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()

const routeId = ref(route.params.id)

// NOTE: Used for viewing order details - NOT confirmation

</script>
<template>
  <div>
    <p>Order details - {{ routeId }}</p>
  </div>
</template>
<style lang="scss" scoped>

</style>

