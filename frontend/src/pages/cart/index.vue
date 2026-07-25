<script setup lang="ts">
import ShoppingCart from '@/components/ShoppingCart.vue'
import { useCartStore } from '@/stores/cart'

const cartStore = useCartStore()
cartStore.loadCart()
</script>

<template>
  <div>
    <ShoppingCart />
  </div>
</template>

<style scoped lang="scss">
div {
  margin: 1rem 0;
}
</style>
