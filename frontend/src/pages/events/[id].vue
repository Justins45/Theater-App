<script setup lang="ts">
import { ref, watch } from 'vue'
import { useRoute } from 'vue-router'
import { api } from '@/api/axios'

const route = useRoute()
const event = ref()

async function getInformation(newId: string) {
  const res = await api.get("/events/" + newId)
  event.value = res.data
}

// if URL updates re fetch
watch(() => route.params.id, (newId) => {
  if (newId) {
    // newId comes as type "string | string[]"
    getInformation(newId.toString())
  }
}, { immediate: true })


</script>

<template>
  <div>
    <h1>{{ event.title}}</h1>
    <p>Show Time: {{ event.showTime }}</p>
    <p>Playing at {{ event.stageName }}</p>
  </div>
</template>

<style scoped lang="scss">

</style>
