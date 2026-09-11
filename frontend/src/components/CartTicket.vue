<script setup lang="ts">

interface CartTicket {
  itemType: string,
  eventName: string,
  stageName: string,
  performanceTime: string,
  section: string,
  row: string,
  seatNumber: number,
  price: string
}

defineProps<{
  item: CartTicket;
}>()
</script>

<template>
<div>
  <p>{{ item.itemType }}: for {{ item.eventName }} at {{ item.stageName }} at {{ item.performanceTime }}</p>
  <p>Seat: {{ item.section }} | {{ item.row }} | {{ item.seatNumber }}</p>
  <p>Price: {{ item.price }}</p>
</div>
</template>

<style scoped lang="scss">

</style>
