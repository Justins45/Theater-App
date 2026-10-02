<script lang="ts" setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'

const router = useRouter()
const route = useRoute()

const routeId = ref(route.params.id)

</script>
<template>
  <div>
    <p>Order Confirmation - {{ routeId }}</p>
  </div>
</template>
<style lang="scss" scoped>

</style>

