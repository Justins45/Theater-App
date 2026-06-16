<script setup lang="ts">
import { ref, watch } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()
const event = ref()

const getInformation = (newId: string | string[]) => {
  event.value = newId
}

// if URL updates re fetch
watch(() => route.params.id, (newId) => {
  if (newId) {
    getInformation(newId)
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
