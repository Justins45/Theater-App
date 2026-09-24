<script setup lang="ts">
import type { CartTicket } from '@theater/shared'

defineProps<{
  item: CartTicket;
}>()
</script>

<template>
  <div class="ticket-card">
    <div class="ticket-card-body">
      <p class="ticket-card-title">{{ item.eventName }}</p>
      <p class="ticket-card-meta">{{ item.stageName }} &middot; {{ item.performanceTime }}</p>
      <div class="ticket-card-foot">
        <span class="ticket-card-seat">{{ item.section }} · {{ item.row }} · {{ item.seatNumber }}</span>
        <span class="ticket-card-price">${{ item.price }}</span>
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss">
.ticket-card {
  display: flex;
  max-width: 18rem;
  border-radius: 6px;
  background: #fff;
  border: 1px solid #e2e2e2;
  overflow: hidden;
  flex: 1;

  &-body {
    padding: 12px 14px;
    flex: 1;
    display: flex;
    flex-direction: column;
    gap: 4px;
  }

  &-title {
    font-weight: 600;
    font-size: 0.9rem;
    color: #1a1a1a;
    margin: 0;
  }

  &-meta {
    font-size: 0.78rem;
    color: #666;
    margin: 0;
  }

  &-foot {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-top: 6px;
  }

  &-seat {
    font-size: 0.75rem;
    color: #555;
    background: #f2f2f2;
    padding: 2px 7px;
    border-radius: 3px;
    letter-spacing: 0.02em;
  }

  &-price {
    font-weight: 600;
    font-size: 0.9rem;
    color: #1a1a1a;
  }
}
</style>
