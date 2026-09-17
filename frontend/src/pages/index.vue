<script setup lang="ts">
import EventSummaryItem from '@components/EventSummaryItem.vue'
import apiClient from '@api'
import { ref } from 'vue'

interface EventItem {
  id: string,
  title: string,
  stageName: string
}

const information = ref<EventItem[]>([])
// get data from api events
async function getInformation() {
  const res = await apiClient.get("/events")
  information.value = res.data
}

getInformation()
</script>

<template>
  <div>
    <h2>Events</h2>
    <template v-if="information.length > 0">
      <div class="event-list">
        <div v-for="item in information" :key="item.id" class="event-item">
            <EventSummaryItem v-bind="item"></EventSummaryItem>
        </div>
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
.event-list {
  margin: 1rem;

  .event-item {
    margin-bottom: 1.75rem;
  }
}
</style>
