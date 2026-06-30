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
const seatList = ref([])
const cartStore = useCartStore()

async function getInformation(pId: string, eId: string) {
  try {
    const res = await apiClient.get("/events/" + eId + "/performances/" + pId + "/seating")
    console.log(res.data)
    seating.value = res.data
  } catch (error: any) {
    router.push({ path: '/404-not-found', state: { originalPath: `/events/${eId}/seating` } })
  }
}

const getSeatClick = (receivedData: any) => {

  clickedSeat.value = receivedData

  if (seatList.value.includes(receivedData.id)) {
    seatList.value = seatList.value.filter(item => item !== receivedData.id)
  } else {
    seatList.value.push(receivedData.id)
  }

}

const addItemsToCart = () => {
  for (const index in seatList.value) {
    cartStore.addToCart(seatList.value[index])
  }
}

// // if URL updates re fetch
watch(() => route.params.performanceId, async (pid) => {
  if (pid) {
    // newId comes as type "string | string[]"
    console.log(pid)
    await getInformation(pid.toString(), eventId.value)
  }
})

// Handle initial load separately, after component is mounted
onMounted(async () => {
  const pid = route.query.performanceId
  const eid = route.params.eventId

  if (pid && eid) {
    eventId.value = eid.toString()
    await getInformation(pid.toString(), eventId.value);
  }
})
</script>

<template>
  <div>
    <h2>Event seating</h2>
    <pre>{{ seatList }}</pre>
    <button v-if="clickedSeat" @click="addItemsToCart">Add items to cart</button>
  </div>
  <template v-if="seating">
    <MainStageMap :seats="seating" @clicked-seat="getSeatClick"/>
    <template v-if="clickedSeat">
      <pre>{{ clickedSeat }}</pre>
    </template>
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
