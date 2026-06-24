<script setup lang="ts">
import { ref, watch, onMounted } from 'vue'
import { useRoute, useRouter, RouterLink } from 'vue-router'
import apiClient from '@/api/axios'

const router = useRouter()
const route = useRoute()
const event = ref()

async function getInformation(newId: string) {
 try {
   const res = await apiClient.get("/events/" + newId)
   event.value = res.data
   // console.log(res.data)
 } catch (error: any) {
   router.push({ path: '/404-not-found', state: { originalPath: `/events/${newId}` } })
 }
}

// // if URL updates re fetch
watch(() => route.params.eventId, async (newId) => {
  if (newId) {
    // newId comes as type "string | string[]"
    await getInformation(newId.toString())
  }
})

// Handle initial load separately, after component is mounted
onMounted(async () => {
  const id = route.params.eventId
  if (id) {
    await getInformation(id.toString())
  }
})


</script>

<template>
  <template v-if="event">
    <div class="container">
      <div>
        <h1>{{ event.title}}</h1>
        <p>Playing at {{ event.stageName }}</p>
      </div>
      <div>
        <h2>Show times</h2>
        <div v-for="item in event.performances" :key="item.eventId">
          <RouterLink :to="'/events/' + event.id + '/seating?performanceId=' + item.id" v-if="item.status != 'CANCELED'">{{ item.showTime }}</RouterLink>
        </div>
      </div>
    </div>
  </template>
</template>

<style scoped lang="scss">
.container {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  max-width: 800px;
}
</style>
