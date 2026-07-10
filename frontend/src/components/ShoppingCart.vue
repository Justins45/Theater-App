<script setup lang="ts">
import { ref } from 'vue'
import { useCartStore } from '@/stores/cart'
import apiClient from '@/api/axios'

const data = ref()
const cartStore = useCartStore()


async function getCart() {
  try {
    const res = await apiClient.get("/cart")
    console.log(res.data)
    data.value = res.data
  } catch (error: any) {
    console.error(error)
  }
}



getCart()

</script>

<template>
  <div class="cart">
    <template v-if="cartStore.cart.length > 0">
      <div class="cart-item" v-for="(item, index) in cartStore.cart" :key="index">
        <div class="cart-item-sub">
          <table>
            <thead>
            <tr>
              <th>SECTION</th>
              <th>ROW</th>
              <th>SEAT NUMBER</th>
              <th>PRICE</th>
            </tr>
            </thead>
            <tbody>
              <tr>
                <td>{{ item.section }}</td>
                <td>{{ item.row }}</td>
                <td>{{ item.seatNumber }}</td>
                <td>${{ item.price }}</td>
              </tr>
            </tbody>
          </table>
        </div>
        <button @click="cartStore.removeFromCart(index)">X</button>
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
    <p>Data:</p>
    <pre>{{ data }}</pre>
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

  .cart-item-sub {
    display: flex;
    flex-direction: row;

    table {
      border-spacing: 8px 0;
    }

    td, th {
      text-align: center;
      padding: 5px 10px;
    }
  }

  button {
    display: block;
  }
}

</style>
