<script setup lang="ts">
import EventSummaryItem from '@/components/EventSummaryItem.vue'
import { api } from '@/api/axios'
import { ref } from 'vue'

const information = ref([])
// get data from api events
async function getInformation() {
  const res = await api.get("/events")
  information.value = res.data
}

getInformation()
</script>

<template>
  <div>
    <h2>Events</h2>
    <template v-if="information.length > 0">
      <div v-for="item in information" :key="item.id" class="event-list">
          <EventSummaryItem v-bind="item"></EventSummaryItem>
      </div>
    </template>
    <template v-else>
      <div>
        <p>No Events to be seen</p>
      </div>
    </template>
  </div>
</template>

<style scoped lang="scss">
div {
  margin-bottom: 1.75rem;
}
</style>
