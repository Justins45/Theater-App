<script setup lang="ts">
import { ref, watch, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import apiClient from '@/api/axios'
import MainStageMap from '@/components/MainStageMap.vue'
import { useCartStore } from '@/stores/cart'

const router = useRouter()
const route = useRoute()
const seating = ref()
const eventId = ref("")
const clickedSeat = ref()
const selectedSeats = ref([])
const cartStore = useCartStore()

async function getInformation(pId: string, eId: string) {
  try {
    const res = await apiClient.get("/events/" + eId + "/performances/" + pId + "/seating")
    // console.log(res.data)
    seating.value = res.data
  } catch (error: any) {
    router.push({ path: '/404-not-found', state: { originalPath: `/events/${eId}/seating` } })
  }
}

async function sendInformation(itemID: string, itemType: string) {
  try {
    const res = await apiClient.post("/cart", {
      itemId: itemID,
      itemType: itemType
    })
    console.log(res.data)
  } catch (error: any) {
    console.error(error)
  }
}

const getSeatClick = (receivedData: any) => {

  clickedSeat.value = receivedData

  if (selectedSeats.value.includes(receivedData)) {
    selectedSeats.value = selectedSeats.value.filter(item => item !== receivedData)
  } else {
    selectedSeats.value.push(receivedData)
  }

}

const addItemsToCart = () => {
  for (const index in selectedSeats.value) {
    cartStore.addToCart(selectedSeats.value[index])
    sendInformation(selectedSeats.value[index].id, "TICKET")
  }
}

// // if URL updates re fetch
watch(() => route.params.performanceId, async (performance_id) => {

  if (performance_id) {
    // performance_id comes as type "string | string[]"
    // console.log(performance_id)
    await getInformation(performance_id.toString(), eventId.value)
  }
})

watch(() => cartStore.cart.map(item => item), (newCartIds, oldCartIds) => {
  const removedIds = oldCartIds.filter(id => !newCartIds.includes(id))

  if (removedIds.length > 0) {
    selectedSeats.value = selectedSeats.value.filter(id => !removedIds.includes(id))
  }
})

// Handle initial load separately, after component is mounted
onMounted(async () => {
  const performance_id = route.query.performanceId
  const event_id = route.params.eventId

  if (performance_id && event_id) {
    eventId.value = event_id.toString()
    await getInformation(performance_id.toString(), eventId.value);
  }
})
</script>

<template>
  <div>
    <h2>Event seating</h2>
    <button v-if="clickedSeat" @click="addItemsToCart">Add items to cart</button>
  </div>
  <template v-if="seating">
    <MainStageMap :seats="seating" :selectedSeats="selectedSeats" @clicked-seat="getSeatClick"/>
    <pre>{{ selectedSeats }}</pre>
  </template>
  <template v-else>
    Loading...
  </template>
</template>

<style scoped lang="scss">
td {
  padding: 0 15px;
}
</style>
