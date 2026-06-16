<script setup lang="ts">
import { ref, watch } from 'vue'
import { useRoute } from 'vue-router'
import { api } from '@/api/axios'

const route = useRoute()
const event = ref()

async function getInformation(newId: string) {
  event.value = await api.get(newId)
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
    <p>GET EVENT BY ID :)</p>
    <p>{{ event }}</p>
  </div>
</template>

<style scoped lang="scss">

</style>
