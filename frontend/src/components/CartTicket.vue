<script setup lang="ts">

defineProps<{
  item: object;
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
