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
      <!-- TODO: Stripe checkout process
        (1) - Link to a checkout page
        (2) - Server generates correct total amount to pay
        (3) - Stripe inside a <iframe> builds the form and the rest of the checkout
        (4) - Sends back a conformation I take and create a Ticket
        (5) - idk what else, but will know when at this point
      -->
      <button type="button" name="checkout" @click="startCheckout">Checkout</button>
    </div>
  </div>
</template>

<style scoped lang="scss">
.cart {
  margin: 1rem 0;
}
</style>
