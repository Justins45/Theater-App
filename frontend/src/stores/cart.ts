import { ref, computed } from "vue";
import { defineStore } from "pinia";

export const useCartStore = defineStore("cart", () => {
  const cart = ref([]);

  // get total items in cart
  const totalItems = computed(() => cart.value.length);

  // add item to cart
  function addToCart(item: any) {
    // TODO: ADD PROPER TICKET UI VIEW
    const exists = cart.value.some(cartItem => cartItem === item);
    if (!exists) {
      cart.value.push(item);
    }
  }

  // remove item from cart
  function removeFromCart(index: number) {
    // TODO: FIX DIS TO BE BETTER (idk dawg my brain foggy)
    cart.value.splice(index, 1);
  }

  // get total price (every addition and deletion + checks)
  // TODO: SOMEHOW GET PRICES FROM ITEMS
  const totalPrice = computed(() => {})

  return {cart, totalItems, addToCart, removeFromCart, totalPrice};

});
