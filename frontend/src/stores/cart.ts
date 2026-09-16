import { ref, computed } from "vue";
import { defineStore } from "pinia";
import apiClient from '@/api/axios'
import type { CartTicket } from "@theater/shared";
export const useCartStore = defineStore("cart", () => {

  const cart = ref<CartTicket[]>([]);
  const loadedCart = ref(false)

  // get total items in cart
  const totalItems = computed(() => cart.value.length);

  function formatToString(num:number): string {
    return num.toFixed(2);
  }

  // add item to cart
  function addToCart(item: CartTicket) {
    const exists = cart.value.some(cartItem => cartItem.id === item.id);
    if (!exists) {
      cart.value.push(item);
    }
  }

  // remove item from cart
  async function removeFromCart(id: string) {
    cart.value = cart.value.filter(cartItem => cartItem.id !== id);

    try {
      await apiClient.delete("/cart", {
        data: { itemId: id }
      });
      await loadCart()
    } catch (error) {
      console.error(error)
    }
  }

  async function loadCart() {
    try {
      if (loadedCart.value) { return }

      const res = await apiClient.get("/cart")
      cart.value = res.data.cartItems
      loadedCart.value = true

    } catch (error) {
      console.error(error)
    }
  }

  function invalidateCart() {
    loadedCart.value = false
  }

  // get total price (every addition and deletion + checks)
  const subtotalNum  = computed(() => {
    return cart.value.reduce((sum, item) => sum + Number(item.price), 0)
  })
  const totalTaxNum  = computed(() => subtotalNum.value * 0.05)
  const totalPriceNum = computed(() => subtotalNum.value + totalTaxNum.value)

  const subtotal   = computed(() => formatToString(subtotalNum.value))
  const totalTax   = computed(() => formatToString(totalTaxNum.value))
  const totalPrice = computed(() => formatToString(totalPriceNum.value))

  return {cart, totalItems, addToCart, removeFromCart, loadCart, subtotal, totalTax, totalPrice, invalidateCart};

});
