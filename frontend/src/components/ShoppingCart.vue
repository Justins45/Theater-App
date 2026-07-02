<script setup lang="ts">
import { useCartStore } from '@/stores/cart'

const cartStore = useCartStore()
</script>

<template>
  <div>
    <template v-if="cartStore.cart.length > 0">
      <p>{{ cartStore.totalItems }}</p>
      <div v-for="(item, index) in cartStore.cart" :key="index">
        <!-- TODO: ADD UI FOR DELETE AND VIEW ITEMS PROPERLY -->
        {{ item }}
      </div>
    </template>
    <template v-else>
      <p>No items in the cart</p>
    </template>
  </div>
</template>

<style scoped lang="scss">

</style>
