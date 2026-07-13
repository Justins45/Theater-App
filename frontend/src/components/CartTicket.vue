<script setup lang="ts">

const props = defineProps<{
  price: string;
}>()
</script>

<template>
<div>
  <p>TICKET</p>
  <p>Price: {{ props.price }}</p>
</div>
</template>

<style scoped lang="scss">

</style>
