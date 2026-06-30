import { ref, computed } from "vue";
import { defineStore } from "pinia";

export const useCartStore = defineStore("cart", () => {
  const cart = ref([])

  const cartSize = computed(() => {
    return cart.value.length;
  })

  const totalPrice = computed(() => {
    let price = 0;
    cart.value.forEach((item) => {price += item.price});
    return price;
  })

  function addToCart(item) {
    cart.value.push(item);
  }

  function removeFromCart(item) {
    cart.value.splice(cart.value.indexOf(item), 1);
  }

  function clearCart() {
    cart.value = [];
  }

  return {cart, cartSize, totalPrice, addToCart, removeFromCart, clearCart};

});
