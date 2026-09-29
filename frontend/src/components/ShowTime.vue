<script lang="ts" setup >
import { computed } from 'vue'
import { RouterLink } from 'vue-router';

const props = defineProps<{
  showTime: string,
  eventId: string,
  itemId: string,
  status: string
 }>()

const parts = computed(() => getDateParts(props.showTime))
const ordinalRules = new Intl.PluralRules('en-US', { type: 'ordinal' })
const SUFFIXES: Record<string, string> = {
  one: 'st', two: 'nd', few: 'rd', other: 'th',
}

// Created once at module load, not on every call
const weekdayFmt = new Intl.DateTimeFormat('en-US', { weekday: 'long' })
const monthFmt = new Intl.DateTimeFormat('en-US', { month: 'long' })
const timeFmt = new Intl.DateTimeFormat('en-US', { hour: 'numeric', minute: '2-digit' })

interface DateParts {
  weekday: string
  month: string
  day: string   // "29th"
  time: string  // "10:55 AM"
}

function getDateParts(input?: string | null): DateParts | null {
  if (!input) return null

  const date = new Date(input)
  if (Number.isNaN(date.getTime())) return null

  const dayNum = date.getDate()

  return {
    weekday: weekdayFmt.format(date),
    month: monthFmt.format(date),
    day: `${dayNum}${SUFFIXES[ordinalRules.select(dayNum)]}`,
    time: timeFmt.format(date),
  }
}

</script>

<template>
  <div>
    <RouterLink :to="'/events/' + eventId + '/seating?performanceId=' + itemId" v-if="status != 'CANCELED'">
      <p v-if="parts">{{ parts.weekday }} {{ parts.month }} {{ parts.day }} at {{ parts.time }}
      </p>
    </RouterLink>
  </div>
</template>
