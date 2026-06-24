<script setup lang="ts">
import { ref, watch, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import apiClient from '@/api/axios'

const router = useRouter()
const route = useRoute()
const seating = ref()
const eventId = ref("")


async function getInformation(pId: string, eId: string) {
  try {
    const res = await apiClient.get("/events/" + eId + "/performances/" + pId + "/seating")
    console.log(res.data)
    seating.value = res.data
  } catch (error: any) {
    router.push({ path: '/404-not-found', state: { originalPath: `/events/${eId}/seating` } })
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
    <table>
      <thead>
      <tr>
        <th>Status</th>
        <th>Row</th>
        <th>Seat</th>
        <th>Section</th>
        <th>Label</th>
      </tr>
      </thead>
      <tbody>
      <tr v-if="!seating">
        <td colspan="5">No seats found.</td>
      </tr>
      <tr v-for="seat in seating" :key="seat.id">
        <td>{{ seat.seatStatus }}</td>
        <td>{{ seat.seat.row }}</td>
        <td>{{ seat.seat.seatNumber }}</td>
        <td>{{ seat.seat.section }}</td>
        <td>{{ seat.seat.uiIdentifier }}</td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<style scoped lang="scss">
td {
  padding: 0 15px;
}
</style>
