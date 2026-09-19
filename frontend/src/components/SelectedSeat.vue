<script lang="ts" setup>
import type { Seat } from '@theater/shared';

defineProps<{
  seat: Seat,
}>()

const emit = defineEmits(['remove-seat'])

function sendUp(data: Seat) {
  emit('remove-seat', data)
}

</script>

<template>
  <div class="selected-seat">
    <div>
      <p class="seat-title">SEC</p>
      <p>{{ seat.section }}</p>
    </div>
    <div>
      <p class="seat-title">ROW</p>
      <p>{{ seat.row }}</p>
    </div>
    <div>
      <p class="seat-title">SEAT</p>
      <p>{{ seat.seatNumber }}</p>
    </div>
    <div>
      <button @click="sendUp(seat)">X</button>
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

  button {
    margin-top: 1rem;
  }
}
</style>
