import { ref, computed } from "vue";
import { defineStore } from "pinia";

export const useCartStore = defineStore("cart", () => {
  const cart = ref([]);

  // get total items in cart


  // add item to cart
  function addToCart(item) {
    if (!cart.value.includes(item)) {
      cart.value.push(item);
    }
  }

  // remove item from cart


  // get total price (every addition and deletion + checks)



  return {cart, addToCart};

});
