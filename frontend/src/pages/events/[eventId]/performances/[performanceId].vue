<script setup lang="ts">
import { ref, watch, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import apiClient from '@/api/axios'

const router = useRouter()
const route = useRoute()
const performance = ref()
const eventId = ref("")

async function getInformation(pId: string, eId: string) {
  try {
    const res = await apiClient.get("/events/" + eId + "/performances/" + pId + "/seating")
    console.log(res.data)
    performance.value = res.data
  } catch (error: any) {
    router.push({ path: '/404-not-found', state: { originalPath: `/events/${eId}/performances/${pId}` } })
  }
}


// events/[eventId]/performances/[performanceId]

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
  const pid = route.params.performanceId
  const eid = route.params.eventId

  if (pid && eid) {
    eventId.value = eid.toString()
    await getInformation(pid.toString(), eventId.value);
  }
})


</script>

<template>
<div>
  <p>Hello Performance</p>
</div>
</template>

<style scoped lang="scss">

</style>
