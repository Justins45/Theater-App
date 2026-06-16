<script setup lang="ts">
import { ref, watch, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import apiClient from '@/api/axios'

const router = useRouter()
const route = useRoute()
const event = ref()

async function getInformation(newId: string) {
 try {
   const res = await apiClient.get("/events/" + newId)
   event.value = res.data
 } catch (error: any) {
   router.push({ path: '/404-not-found', state: { originalPath: `/events/${newId}` } })
 }
}

// if URL updates re fetch
watch(() => route.params.id, async (newId) => {
  if (newId) {
    // newId comes as type "string | string[]"
    await getInformation(newId.toString())
  }
})

// Handle initial load separately, after component is mounted
onMounted(async () => {
  const id = route.params.id
  if (id) {
    await getInformation(id.toString())
  }
})


</script>

<template>
<template v-if="event">
  <div>
    <h1>{{ event.title}}</h1>
    <p>Show Time: {{ event.showTime }}</p>
    <p>Playing at {{ event.stageName }}</p>
  </div>
</template>
</template>

<style scoped lang="scss">

</style>
