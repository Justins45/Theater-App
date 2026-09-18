<script setup lang="ts">
import { ref, watch, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import apiClient from '@api'
import MainStageMap from '@components/MainStageMap.vue'
import { useCartStore } from '@stores/cart'
import { useLoggedInStore } from '@stores/loggedIn'
import { useRouteData } from '@composable/useRouteData'
import type { Seat } from '@theater/shared'
import SelectedSeat from '@/components/SelectedSeat.vue'

const router = useRouter()
const route = useRoute()
const seating = ref()
const eventId = ref("")
const performanceInfo = ref()
const clickedSeat = ref()
const selectedSeats = ref<Seat[]>([])
const cartStore = useCartStore()
const loggedInStore = useLoggedInStore()

interface TransferTicket {
  id: string | undefined,
  itemType: string
}

async function getInformation(pId: string, eId: string) {
  console.log(pId, eId)
  try {
    const res = await apiClient.get("/events/" + eId + "/performances/" + pId + "/seating")
    const res2 = await apiClient.get("/events/" + eId + "/performances/" + pId + "/info")
    // console.log(res.data)
    seating.value = res.data
    performanceInfo.value = res2.data
  } catch (error) {
    router.push({ path: '/404-not-found', state: { originalPath: `/events/${eId}/seating` } })
    console.log(`Error :: ${error}`)
  }
}

async function sendInformation(item: TransferTicket) {
  try {
    const res = await apiClient.post("/cart", {
      itemId: item.id,
      itemType: item.itemType
    })
    console.log(res.data)
  } catch (error) {
    console.error(error)
  }
}

const getSeatClick = (receivedData: Seat) => {

  if (receivedData)
  clickedSeat.value = receivedData

  if (selectedSeats.value.includes(receivedData)) {
    selectedSeats.value = selectedSeats.value.filter(item => item !== receivedData)
  } else {
    selectedSeats.value.push(receivedData)
  }

}

const addItemsToCart = () => {
  if (!loggedInStore.loggedIn) {
    router.push("/account/login")
    return
  }

  for (const index in selectedSeats.value) {
    if (!performanceInfo.value || !selectedSeats.value[index]) return
    cartStore.addToCart({
      eventName: performanceInfo.value.eventName,
      id: selectedSeats.value[index].id,
      itemType: "TICKET",
      performanceTime: performanceInfo.value.performanceTime,
      price: selectedSeats.value[index].price,
      row: selectedSeats.value[index].row,
      seatNumber: selectedSeats.value[index].seatNumber,
      section: selectedSeats.value[index].section,
      stageName: performanceInfo.value.stageName
    })
    sendInformation({
      id: selectedSeats.value[index]?.id,
      itemType: "TICKET",
    })
  }
}

// // if URL updates re fetch
useRouteData(['eventId'], async ({ eventId }) => {
  // fetch your data
  const performance_id = route.query.performanceId as string
  await getInformation(performance_id, eventId)
})

watch(() => cartStore.cart?.map(item => item.id) ?? [], (newCartIds, oldCartIds) => {
  if (!loggedInStore.loggedIn) return

  const removedIds = oldCartIds.filter(id => !newCartIds.includes(id))

  if (removedIds.length > 0) {
    selectedSeats.value = selectedSeats.value.filter(seat => !removedIds.includes(seat.id))
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

    <template v-if="selectedSeats.length > 0">
      <h2>Selected Seats</h2>
      <div>
        <div v-for="(seat, index) in selectedSeats" :key="index" class="selected-ticket" >
          <SelectedSeat :section="seat.section" :row="seat.row" :seat-number="seat.seatNumber" />
        </div>
      </div>
    </template>

  </template>
  <template v-else>
    Loading...
  </template>
</template>

<style scoped lang="scss">

</style>
