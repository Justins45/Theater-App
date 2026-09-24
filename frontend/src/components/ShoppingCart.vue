<script setup lang="ts">
import { useCartStore } from '@stores/cart'
import CartTicket from '@components/CartTicket.vue'
const cartStore = useCartStore()

</script>

<template>
  <div class="cart">
    <template v-if="cartStore.cart.length > 0">
      <div class="cart-item" v-for="item in cartStore.cart" :key="item.id">
        <template v-if="item.itemType === 'TICKET'">
          <CartTicket :item="item" />
        </template>
        <template v-else>
          <p>Not an item lol</p>
        </template>
        <button class="cart-item-remove" @click="cartStore.removeFromCart(item.id)">✕</button>
      </div>

      <div class="cart-summary">
        <div class="cart-summary-row">
          <span>Tickets</span><span>{{ cartStore.totalItems }}</span>
        </div>
        <div class="cart-summary-row">
          <span>Subtotal</span><span>${{ cartStore.subtotal }}</span>
        </div>
        <div class="cart-summary-row">
          <span>Tax</span><span>${{ cartStore.totalTax }}</span>
        </div>
        <div class="cart-summary-row cart-summary-row-total">
          <span>Total</span><span>${{ cartStore.totalPrice }}</span>
        </div>
      </div>
    </template>
    <template v-else>
      <p class="no-items">No items in the cart</p>
    </template>
  </div>
</template>

<style scoped lang="scss">
.cart {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.no-items {
  margin-left: 1rem;
}



.cart-item {
  display: flex;
  align-items: stretch;
  gap: 10px;
  margin-bottom: 10px;

  &-remove {
    flex-shrink: 0;
    align-self: center;
    width: 28px;
    height: 28px;
    border-radius: 50%;
    border: 1px solid #ddd;
    background: transparent;
    color: #999;
    cursor: pointer;
    font-size: 0.7rem;
    line-height: 1;
    transition: background 0.15s, color 0.15s, border-color 0.15s;

    &:hover {
      background: #fff0f0;
      color: #8b1a1a;
      border-color: #8b1a1a;
    }
  }
}

.cart-summary {
  margin-top: 16px;
  padding-top: 14px;
  border-top: 1px solid #e2e2e2;
  max-width: 18rem;

  &-row {
    display: flex;
    justify-content: space-between;
    font-size: 0.85rem;
    color: #555;
    padding: 3px 0;

    &-total {
      margin-top: 10px;
      padding-top: 10px;
      border-top: 1px solid #e2e2e2;
      font-weight: 700;
      font-size: 0.95rem;
      color: #1a1a1a;
    }
  }
}
</style>
