<script lang="ts" setup>
defineProps<{
  section: string,
  row: string,
  seatNumber: number
}>()
</script>

<template>
  <div class="selected-seat">
    <div>
      <p class="seat-title">SEC</p>
      <p>{{ section }}</p>
    </div>
    <div>
      <p class="seat-title">ROW</p>
      <p>{{ row }}</p>
    </div>
    <div>
      <p class="seat-title">SEAT</p>
      <p>{{ seatNumber }}</p>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.seat-title {
  font-size: 1rem;
  font-weight: 800;
}

.selected-seat {
  display: flex;
  flex-direction: row;
  max-width: 15rem;
  justify-content: space-between;
  background-color: lightgrey;
  border-radius: 5px;
  padding: 0 1rem;
  margin-bottom: 1rem;
}
</style>
