<script setup lang="ts">
import EventSummaryItem from '@/components/EventSummaryItem.vue'
// get data from api events
const data = [
  {
    "id": "019eacb8-f6e4-77da-afda-86d103b67915",
    "title": "First Event yay!",
    "showTime": "17:15",
    "stageId": 1,
    "stageName": "main stage"
  },
  {
    "id": "019eb33d-aafe-716c-99c3-d65c5d2cfe67",
    "title": "Cat Sitting the Musical!",
    "showTime": "09:55",
    "stageId": 1,
    "stageName": "main stage"
  }
]
</script>

<template>
  <div>
    <h2>Events</h2>
    <div v-for="item in data" :key="item.id" class="event">
        <EventSummaryItem v-bind="item"></EventSummaryItem>
    </div>
  </div>
</template>

<style scoped lang="scss">
</style>
