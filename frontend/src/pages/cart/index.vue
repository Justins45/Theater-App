<script setup lang="ts">
import ShoppingCart from '@/components/ShoppingCart.vue'
import { useCartStore } from '@/stores/cart'

const cartStore = useCartStore()
cartStore.loadCart()

async function startCheckout() {
  console.log('start checkout initiated')
}

</script>

<template>
  <div class="cart">
    <ShoppingCart />
    <div v-if="cartStore.totalItems > 0">
      <button type="button" name="checkout" @click="startCheckout">Checkout</button>
    </div>
  </div>
</template>

<style scoped lang="scss">
.cart {
  margin: 1rem 0;
}
</style>
