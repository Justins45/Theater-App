<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute, useRouter, RouterLink } from 'vue-router'
import { useRouteData } from '@composable/useRouteData'
import apiClient from '@api'

const router = useRouter()
const route = useRoute()
const event = ref()

const ordinalRules = new Intl.PluralRules('en-US', { type: 'ordinal' })
const SUFFIXES: Record<string, string> = {
  one: 'st', two: 'nd', few: 'rd', other: 'th',
}

function formatDateTime(input?: string | null): string {
  if (!input) return ''

  const date = new Date(input)
  if (Number.isNaN(date.getTime())) return ''

  const weekday = date.toLocaleDateString('en-US', { weekday: 'long' })
  const month = date.toLocaleDateString('en-US', { month: 'long' })
  const day = date.getDate()

  return `${weekday} ${month} ${day}${SUFFIXES[ordinalRules.select(day)]}`
} // "Tuesday September 29th"

async function getInformation(newId: string) {
 try {
   const res = await apiClient.get("/events/" + newId)
   event.value = res.data
   // console.log(res.data)
 } catch (error) {
   await router.push({ path: '/404-not-found', state: { originalPath: `/events/${newId}` } })
   console.log(`Error :: ${error}`)
 }
}

// if URL updates re fetch
useRouteData(['eventId'], async ({ eventId}) => {
   await getInformation(eventId)
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
      <div class="event-details">
        <h1>{{ event.title}}</h1>
        <p>{{ event.description }}</p>
        <p>Playing at {{ event.stageName }}</p>
      </div>
      <div class="divider"></div>
      <div>
        <h2>Show times</h2>
        <!-- TODO: make this a slot to handle the map with the event details + changing showtime?  -->
        <template v-if="event.performances.length > 0">
          <div v-for="item in event.performances" :key="item.eventId">

            <RouterLink :to="'/events/' + event.id + '/seating?performanceId=' + item.id" v-if="item.status != 'CANCELED'">{{ formatDateTime(item.showTime) }}</RouterLink>

          </div>
        </template>
        <template v-else>
          <p>No Performances available</p>
        </template>
      </div>
    </div>
  </template>
</template>

<style scoped lang="scss">
.event-details {
  margin-bottom: 2rem;
}

.divider {
  width: 100%;
  height: 2px;
  border-radius: 5px;
  background-color: lightgrey;
}

.container {
  max-width: 800px;
}
</style>
