<script setup lang="ts">
import { useCartStore } from '@/stores/cart'
import CartTicket from '@/components/CartTicket.vue'
const cartStore = useCartStore()

</script>

<template>
  <div class="cart">
    <template v-if="cartStore.cart.length > 0">
      <div class="cart-item" v-for="item in cartStore.cart" :key="item.id">
        <template v-if="item.itemType === 'TICKET'">
          <CartTicket :price="item.unitPrice" />
        </template>
        <template v-else>
          <p>Not an item lol</p>
        </template>
        <button @click="cartStore.removeFromCart(item.id)">X</button>
      </div>
      <div>
        <!-- Total tickets # + Total Cost + breakdown -->
        <p>Total Tickets: {{ cartStore.totalItems }}</p>
        <p>Subtotal: ${{ cartStore.subtotal }}</p>
        <p>Tax: ${{ cartStore.totalTax }}</p>
        <p>Total Price: ${{ cartStore.totalPrice }}</p>
      </div>

    </template>
    <template v-else>
      <p>No items in the cart</p>
    </template>
  </div>
</template>

<style scoped lang="scss">
.cart {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  background-color: #dfe1e6;
}

.cart-item {
  border: #222222 solid 1px;
  width: 27rem;
  padding: 0.25rem 1rem;
  display: flex;
  border-radius: 0.2rem;

  button {
    display: block;
  }
}

</style>
