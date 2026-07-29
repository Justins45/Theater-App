<script setup lang="ts">
import { watch, ref, onMounted } from 'vue'

const emit = defineEmits(['clicked-seat'])
const props = defineProps<{
  seats: Array<any>,
  selectedSeats: Array<any>,
}>()
const svgMap = ref()

function sendUp(data: any) {
  emit('clicked-seat', data)
}

function selectSeat(event: PointerEvent) {
  const target = event.target as HTMLElement | SVGElement | null

  const e = target?.closest<SVGElement>('[data-seat-id]')

  if (!e) return null
  const uiIdentifier = e.dataset.seatId

  if (props.seats.length < 1) { return null }
  const clickedSeat = props.seats.find(seat => seat.uiIdentifier === uiIdentifier)

  sendUp(clickedSeat)
}

function applyUnavailableClasses() {
  const statusMap = new Map(props.seats.map(s => [s.uiIdentifier, s.seatStatus]))

  svgMap.value?.querySelectorAll('[data-seat-id]').forEach(el => {
    const status = statusMap.get(el.getAttribute('data-seat-id') ?? '')
    el.classList.toggle('unavailable', status === 'SOLD' || status === 'HELD')
  })
}

watch(() => props.selectedSeats, (newIds) => {

  svgMap.value?.querySelectorAll(`[data-seat-id]`).forEach( el => {
    el.classList.remove('selected')
  })

  newIds.forEach( id => {
    svgMap.value?.querySelector(`[data-seat-id="${id.uiIdentifier}"]`)?.classList.add('selected')
  })
  }, { deep: true }
)

onMounted(() => {
  applyUnavailableClasses()
})

</script>

<template>
  <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1208 388" width="1208" height="388" @click="selectSeat" ref="svgMap">
    <rect class="stage" x="44" y="35" width="1134" height="40" />
    <text class="stage-label" x="611.0" y="55.0">STAGE</text>
    <text class="section-label" x="160.0" y="98">LEFT</text>
    <text class="section-label" x="611.0" y="98">MAIN</text>
    <text class="section-label" x="1062.0" y="98">RIGHT</text>
    <text class="row-label" x="28.0" y="128.0">A</text>
    <rect class="seat" data-seat-id="left-A-1" x="44" y="114" width="28" height="28"><title>
      left-A-1</title></rect>
    <rect class="seat" data-seat-id="left-A-2" x="78" y="114" width="28" height="28"><title>
      left-A-2</title></rect>
    <rect class="seat" data-seat-id="left-A-3" x="112" y="114" width="28" height="28"><title>
      left-A-3</title></rect>
    <rect class="seat" data-seat-id="left-A-4" x="146" y="114" width="28" height="28"><title>
      left-A-4</title></rect>
    <rect class="seat" data-seat-id="left-A-5" x="180" y="114" width="28" height="28"><title>
      left-A-5</title></rect>
    <rect class="seat" data-seat-id="left-A-6" x="214" y="114" width="28" height="28"><title>
      left-A-6</title></rect>
    <rect class="seat" data-seat-id="left-A-7" x="248" y="114" width="28" height="28"><title>
      left-A-7</title></rect>
    <rect class="seat" data-seat-id="main-A-1" x="308" y="114" width="28" height="28"><title>
      main-A-1</title></rect>
    <rect class="seat" data-seat-id="main-A-2" x="342" y="114" width="28" height="28"><title>
      main-A-2</title></rect>
    <rect class="seat" data-seat-id="main-A-3" x="376" y="114" width="28" height="28"><title>
      main-A-3</title></rect>
    <rect class="seat" data-seat-id="main-A-4" x="410" y="114" width="28" height="28"><title>
      main-A-4</title></rect>
    <rect class="seat" data-seat-id="main-A-5" x="444" y="114" width="28" height="28"><title>
      main-A-5</title></rect>
    <rect class="seat" data-seat-id="main-A-6" x="478" y="114" width="28" height="28"><title>
      main-A-6</title></rect>
    <rect class="seat" data-seat-id="main-A-7" x="512" y="114" width="28" height="28"><title>
      main-A-7</title></rect>
    <rect class="seat" data-seat-id="main-A-8" x="546" y="114" width="28" height="28"><title>
      main-A-8</title></rect>
    <rect class="seat" data-seat-id="main-A-9" x="580" y="114" width="28" height="28"><title>
      main-A-9</title></rect>
    <rect class="seat" data-seat-id="main-A-10" x="614" y="114" width="28" height="28"><title>
      main-A-10</title></rect>
    <rect class="seat" data-seat-id="main-A-11" x="648" y="114" width="28" height="28"><title>
      main-A-11</title></rect>
    <rect class="seat" data-seat-id="main-A-12" x="682" y="114" width="28" height="28"><title>
      main-A-12</title></rect>
    <rect class="seat" data-seat-id="main-A-13" x="716" y="114" width="28" height="28"><title>
      main-A-13</title></rect>
    <rect class="seat" data-seat-id="main-A-14" x="750" y="114" width="28" height="28"><title>
      main-A-14</title></rect>
    <rect class="seat" data-seat-id="main-A-15" x="784" y="114" width="28" height="28"><title>
      main-A-15</title></rect>
    <rect class="seat" data-seat-id="main-A-16" x="818" y="114" width="28" height="28"><title>
      main-A-16</title></rect>
    <rect class="seat" data-seat-id="main-A-17" x="852" y="114" width="28" height="28"><title>
      main-A-17</title></rect>
    <rect class="seat" data-seat-id="main-A-18" x="886" y="114" width="28" height="28"><title>
      main-A-18</title></rect>
    <rect class="seat" data-seat-id="right-A-1" x="946" y="114" width="28" height="28"><title>
      right-A-1</title></rect>
    <rect class="seat" data-seat-id="right-A-2" x="980" y="114" width="28" height="28"><title>
      right-A-2</title></rect>
    <rect class="seat" data-seat-id="right-A-3" x="1014" y="114" width="28" height="28"><title>
      right-A-3</title></rect>
    <rect class="seat" data-seat-id="right-A-4" x="1048" y="114" width="28" height="28"><title>
      right-A-4</title></rect>
    <rect class="seat" data-seat-id="right-A-5" x="1082" y="114" width="28" height="28"><title>
      right-A-5</title></rect>
    <rect class="seat" data-seat-id="right-A-6" x="1116" y="114" width="28" height="28"><title>
      right-A-6</title></rect>
    <rect class="seat" data-seat-id="right-A-7" x="1150" y="114" width="28" height="28"><title>
      right-A-7</title></rect>
    <text class="row-label" x="28.0" y="162.0">B</text>
    <rect class="seat" data-seat-id="left-B-1" x="44" y="148" width="28" height="28"><title>
      left-B-1</title></rect>
    <rect class="seat" data-seat-id="left-B-2" x="78" y="148" width="28" height="28"><title>
      left-B-2</title></rect>
    <rect class="seat" data-seat-id="left-B-3" x="112" y="148" width="28" height="28"><title>
      left-B-3</title></rect>
    <rect class="seat" data-seat-id="left-B-4" x="146" y="148" width="28" height="28"><title>
      left-B-4</title></rect>
    <rect class="seat" data-seat-id="left-B-5" x="180" y="148" width="28" height="28"><title>
      left-B-5</title></rect>
    <rect class="seat" data-seat-id="left-B-6" x="214" y="148" width="28" height="28"><title>
      left-B-6</title></rect>
    <rect class="seat" data-seat-id="left-B-7" x="248" y="148" width="28" height="28"><title>
      left-B-7</title></rect>
    <rect class="seat" data-seat-id="main-B-1" x="308" y="148" width="28" height="28"><title>
      main-B-1</title></rect>
    <rect class="seat" data-seat-id="main-B-2" x="342" y="148" width="28" height="28"><title>
      main-B-2</title></rect>
    <rect class="seat" data-seat-id="main-B-3" x="376" y="148" width="28" height="28"><title>
      main-B-3</title></rect>
    <rect class="seat" data-seat-id="main-B-4" x="410" y="148" width="28" height="28"><title>
      main-B-4</title></rect>
    <rect class="seat" data-seat-id="main-B-5" x="444" y="148" width="28" height="28"><title>
      main-B-5</title></rect>
    <rect class="seat" data-seat-id="main-B-6" x="478" y="148" width="28" height="28"><title>
      main-B-6</title></rect>
    <rect class="seat" data-seat-id="main-B-7" x="512" y="148" width="28" height="28"><title>
      main-B-7</title></rect>
    <rect class="seat" data-seat-id="main-B-8" x="546" y="148" width="28" height="28"><title>
      main-B-8</title></rect>
    <rect class="seat" data-seat-id="main-B-9" x="580" y="148" width="28" height="28"><title>
      main-B-9</title></rect>
    <rect class="seat" data-seat-id="main-B-10" x="614" y="148" width="28" height="28"><title>
      main-B-10</title></rect>
    <rect class="seat" data-seat-id="main-B-11" x="648" y="148" width="28" height="28"><title>
      main-B-11</title></rect>
    <rect class="seat" data-seat-id="main-B-12" x="682" y="148" width="28" height="28"><title>
      main-B-12</title></rect>
    <rect class="seat" data-seat-id="main-B-13" x="716" y="148" width="28" height="28"><title>
      main-B-13</title></rect>
    <rect class="seat" data-seat-id="main-B-14" x="750" y="148" width="28" height="28"><title>
      main-B-14</title></rect>
    <rect class="seat" data-seat-id="main-B-15" x="784" y="148" width="28" height="28"><title>
      main-B-15</title></rect>
    <rect class="seat" data-seat-id="main-B-16" x="818" y="148" width="28" height="28"><title>
      main-B-16</title></rect>
    <rect class="seat" data-seat-id="main-B-17" x="852" y="148" width="28" height="28"><title>
      main-B-17</title></rect>
    <rect class="seat" data-seat-id="main-B-18" x="886" y="148" width="28" height="28"><title>
      main-B-18</title></rect>
    <rect class="seat" data-seat-id="right-B-1" x="946" y="148" width="28" height="28"><title>
      right-B-1</title></rect>
    <rect class="seat" data-seat-id="right-B-2" x="980" y="148" width="28" height="28"><title>
      right-B-2</title></rect>
    <rect class="seat" data-seat-id="right-B-3" x="1014" y="148" width="28" height="28"><title>
      right-B-3</title></rect>
    <rect class="seat" data-seat-id="right-B-4" x="1048" y="148" width="28" height="28"><title>
      right-B-4</title></rect>
    <rect class="seat" data-seat-id="right-B-5" x="1082" y="148" width="28" height="28"><title>
      right-B-5</title></rect>
    <rect class="seat" data-seat-id="right-B-6" x="1116" y="148" width="28" height="28"><title>
      right-B-6</title></rect>
    <rect class="seat" data-seat-id="right-B-7" x="1150" y="148" width="28" height="28"><title>
      right-B-7</title></rect>
    <text class="row-label" x="28.0" y="196.0">C</text>
    <rect class="seat" data-seat-id="left-C-1" x="44" y="182" width="28" height="28"><title>
      left-C-1</title></rect>
    <rect class="seat" data-seat-id="left-C-2" x="78" y="182" width="28" height="28"><title>
      left-C-2</title></rect>
    <rect class="seat" data-seat-id="left-C-3" x="112" y="182" width="28" height="28"><title>
      left-C-3</title></rect>
    <rect class="seat" data-seat-id="left-C-4" x="146" y="182" width="28" height="28"><title>
      left-C-4</title></rect>
    <rect class="seat" data-seat-id="left-C-5" x="180" y="182" width="28" height="28"><title>
      left-C-5</title></rect>
    <rect class="seat" data-seat-id="left-C-6" x="214" y="182" width="28" height="28"><title>
      left-C-6</title></rect>
    <rect class="seat" data-seat-id="left-C-7" x="248" y="182" width="28" height="28"><title>
      left-C-7</title></rect>
    <rect class="seat" data-seat-id="main-C-1" x="308" y="182" width="28" height="28"><title>
      main-C-1</title></rect>
    <rect class="seat" data-seat-id="main-C-2" x="342" y="182" width="28" height="28"><title>
      main-C-2</title></rect>
    <rect class="seat" data-seat-id="main-C-3" x="376" y="182" width="28" height="28"><title>
      main-C-3</title></rect>
    <rect class="seat" data-seat-id="main-C-4" x="410" y="182" width="28" height="28"><title>
      main-C-4</title></rect>
    <rect class="seat" data-seat-id="main-C-5" x="444" y="182" width="28" height="28"><title>
      main-C-5</title></rect>
    <rect class="seat" data-seat-id="main-C-6" x="478" y="182" width="28" height="28"><title>
      main-C-6</title></rect>
    <rect class="seat" data-seat-id="main-C-7" x="512" y="182" width="28" height="28"><title>
      main-C-7</title></rect>
    <rect class="seat" data-seat-id="main-C-8" x="546" y="182" width="28" height="28"><title>
      main-C-8</title></rect>
    <rect class="seat" data-seat-id="main-C-9" x="580" y="182" width="28" height="28"><title>
      main-C-9</title></rect>
    <rect class="seat" data-seat-id="main-C-10" x="614" y="182" width="28" height="28"><title>
      main-C-10</title></rect>
    <rect class="seat" data-seat-id="main-C-11" x="648" y="182" width="28" height="28"><title>
      main-C-11</title></rect>
    <rect class="seat" data-seat-id="main-C-12" x="682" y="182" width="28" height="28"><title>
      main-C-12</title></rect>
    <rect class="seat" data-seat-id="main-C-13" x="716" y="182" width="28" height="28"><title>
      main-C-13</title></rect>
    <rect class="seat" data-seat-id="main-C-14" x="750" y="182" width="28" height="28"><title>
      main-C-14</title></rect>
    <rect class="seat" data-seat-id="main-C-15" x="784" y="182" width="28" height="28"><title>
      main-C-15</title></rect>
    <rect class="seat" data-seat-id="main-C-16" x="818" y="182" width="28" height="28"><title>
      main-C-16</title></rect>
    <rect class="seat" data-seat-id="main-C-17" x="852" y="182" width="28" height="28"><title>
      main-C-17</title></rect>
    <rect class="seat" data-seat-id="main-C-18" x="886" y="182" width="28" height="28"><title>
      main-C-18</title></rect>
    <rect class="seat" data-seat-id="right-C-1" x="946" y="182" width="28" height="28"><title>
      right-C-1</title></rect>
    <rect class="seat" data-seat-id="right-C-2" x="980" y="182" width="28" height="28"><title>
      right-C-2</title></rect>
    <rect class="seat" data-seat-id="right-C-3" x="1014" y="182" width="28" height="28"><title>
      right-C-3</title></rect>
    <rect class="seat" data-seat-id="right-C-4" x="1048" y="182" width="28" height="28"><title>
      right-C-4</title></rect>
    <rect class="seat" data-seat-id="right-C-5" x="1082" y="182" width="28" height="28"><title>
      right-C-5</title></rect>
    <rect class="seat" data-seat-id="right-C-6" x="1116" y="182" width="28" height="28"><title>
      right-C-6</title></rect>
    <rect class="seat" data-seat-id="right-C-7" x="1150" y="182" width="28" height="28"><title>
      right-C-7</title></rect>
    <text class="row-label" x="28.0" y="230.0">D</text>
    <rect class="seat" data-seat-id="left-D-1" x="44" y="216" width="28" height="28"><title>
      left-D-1</title></rect>
    <rect class="seat" data-seat-id="left-D-2" x="78" y="216" width="28" height="28"><title>
      left-D-2</title></rect>
    <rect class="seat" data-seat-id="left-D-3" x="112" y="216" width="28" height="28"><title>
      left-D-3</title></rect>
    <rect class="seat" data-seat-id="left-D-4" x="146" y="216" width="28" height="28"><title>
      left-D-4</title></rect>
    <rect class="seat" data-seat-id="left-D-5" x="180" y="216" width="28" height="28"><title>
      left-D-5</title></rect>
    <rect class="seat" data-seat-id="left-D-6" x="214" y="216" width="28" height="28"><title>
      left-D-6</title></rect>
    <rect class="seat" data-seat-id="left-D-7" x="248" y="216" width="28" height="28"><title>
      left-D-7</title></rect>
    <rect class="seat" data-seat-id="main-D-1" x="308" y="216" width="28" height="28"><title>
      main-D-1</title></rect>
    <rect class="seat" data-seat-id="main-D-2" x="342" y="216" width="28" height="28"><title>
      main-D-2</title></rect>
    <rect class="seat" data-seat-id="main-D-3" x="376" y="216" width="28" height="28"><title>
      main-D-3</title></rect>
    <rect class="seat" data-seat-id="main-D-4" x="410" y="216" width="28" height="28"><title>
      main-D-4</title></rect>
    <rect class="seat" data-seat-id="main-D-5" x="444" y="216" width="28" height="28"><title>
      main-D-5</title></rect>
    <rect class="seat" data-seat-id="main-D-6" x="478" y="216" width="28" height="28"><title>
      main-D-6</title></rect>
    <rect class="seat" data-seat-id="main-D-7" x="512" y="216" width="28" height="28"><title>
      main-D-7</title></rect>
    <rect class="seat" data-seat-id="main-D-8" x="546" y="216" width="28" height="28"><title>
      main-D-8</title></rect>
    <rect class="seat" data-seat-id="main-D-9" x="580" y="216" width="28" height="28"><title>
      main-D-9</title></rect>
    <rect class="seat" data-seat-id="main-D-10" x="614" y="216" width="28" height="28"><title>
      main-D-10</title></rect>
    <rect class="seat" data-seat-id="main-D-11" x="648" y="216" width="28" height="28"><title>
      main-D-11</title></rect>
    <rect class="seat" data-seat-id="main-D-12" x="682" y="216" width="28" height="28"><title>
      main-D-12</title></rect>
    <rect class="seat" data-seat-id="main-D-13" x="716" y="216" width="28" height="28"><title>
      main-D-13</title></rect>
    <rect class="seat" data-seat-id="main-D-14" x="750" y="216" width="28" height="28"><title>
      main-D-14</title></rect>
    <rect class="seat" data-seat-id="main-D-15" x="784" y="216" width="28" height="28"><title>
      main-D-15</title></rect>
    <rect class="seat" data-seat-id="main-D-16" x="818" y="216" width="28" height="28"><title>
      main-D-16</title></rect>
    <rect class="seat" data-seat-id="main-D-17" x="852" y="216" width="28" height="28"><title>
      main-D-17</title></rect>
    <rect class="seat" data-seat-id="main-D-18" x="886" y="216" width="28" height="28"><title>
      main-D-18</title></rect>
    <rect class="seat" data-seat-id="right-D-1" x="946" y="216" width="28" height="28"><title>
      right-D-1</title></rect>
    <rect class="seat" data-seat-id="right-D-2" x="980" y="216" width="28" height="28"><title>
      right-D-2</title></rect>
    <rect class="seat" data-seat-id="right-D-3" x="1014" y="216" width="28" height="28"><title>
      right-D-3</title></rect>
    <rect class="seat" data-seat-id="right-D-4" x="1048" y="216" width="28" height="28"><title>
      right-D-4</title></rect>
    <rect class="seat" data-seat-id="right-D-5" x="1082" y="216" width="28" height="28"><title>
      right-D-5</title></rect>
    <rect class="seat" data-seat-id="right-D-6" x="1116" y="216" width="28" height="28"><title>
      right-D-6</title></rect>
    <rect class="seat" data-seat-id="right-D-7" x="1150" y="216" width="28" height="28"><title>
      right-D-7</title></rect>
    <text class="row-label" x="28.0" y="264.0">E</text>
    <rect class="seat" data-seat-id="left-E-1" x="44" y="250" width="28" height="28"><title>
      left-E-1</title></rect>
    <rect class="seat" data-seat-id="left-E-2" x="78" y="250" width="28" height="28"><title>
      left-E-2</title></rect>
    <rect class="seat" data-seat-id="left-E-3" x="112" y="250" width="28" height="28"><title>
      left-E-3</title></rect>
    <rect class="seat" data-seat-id="left-E-4" x="146" y="250" width="28" height="28"><title>
      left-E-4</title></rect>
    <rect class="seat" data-seat-id="left-E-5" x="180" y="250" width="28" height="28"><title>
      left-E-5</title></rect>
    <rect class="seat" data-seat-id="left-E-6" x="214" y="250" width="28" height="28"><title>
      left-E-6</title></rect>
    <rect class="seat" data-seat-id="left-E-7" x="248" y="250" width="28" height="28"><title>
      left-E-7</title></rect>
    <rect class="seat" data-seat-id="main-E-1" x="308" y="250" width="28" height="28"><title>
      main-E-1</title></rect>
    <rect class="seat" data-seat-id="main-E-2" x="342" y="250" width="28" height="28"><title>
      main-E-2</title></rect>
    <rect class="seat" data-seat-id="main-E-3" x="376" y="250" width="28" height="28"><title>
      main-E-3</title></rect>
    <rect class="seat" data-seat-id="main-E-4" x="410" y="250" width="28" height="28"><title>
      main-E-4</title></rect>
    <rect class="seat" data-seat-id="main-E-5" x="444" y="250" width="28" height="28"><title>
      main-E-5</title></rect>
    <rect class="seat" data-seat-id="main-E-6" x="478" y="250" width="28" height="28"><title>
      main-E-6</title></rect>
    <rect class="seat" data-seat-id="main-E-7" x="512" y="250" width="28" height="28"><title>
      main-E-7</title></rect>
    <rect class="seat" data-seat-id="main-E-8" x="546" y="250" width="28" height="28"><title>
      main-E-8</title></rect>
    <rect class="seat" data-seat-id="main-E-9" x="580" y="250" width="28" height="28"><title>
      main-E-9</title></rect>
    <rect class="seat" data-seat-id="main-E-10" x="614" y="250" width="28" height="28"><title>
      main-E-10</title></rect>
    <rect class="seat" data-seat-id="main-E-11" x="648" y="250" width="28" height="28"><title>
      main-E-11</title></rect>
    <rect class="seat" data-seat-id="main-E-12" x="682" y="250" width="28" height="28"><title>
      main-E-12</title></rect>
    <rect class="seat" data-seat-id="main-E-13" x="716" y="250" width="28" height="28"><title>
      main-E-13</title></rect>
    <rect class="seat" data-seat-id="main-E-14" x="750" y="250" width="28" height="28"><title>
      main-E-14</title></rect>
    <rect class="seat" data-seat-id="main-E-15" x="784" y="250" width="28" height="28"><title>
      main-E-15</title></rect>
    <rect class="seat" data-seat-id="main-E-16" x="818" y="250" width="28" height="28"><title>
      main-E-16</title></rect>
    <rect class="seat" data-seat-id="main-E-17" x="852" y="250" width="28" height="28"><title>
      main-E-17</title></rect>
    <rect class="seat" data-seat-id="main-E-18" x="886" y="250" width="28" height="28"><title>
      main-E-18</title></rect>
    <rect class="seat" data-seat-id="right-E-1" x="946" y="250" width="28" height="28"><title>
      right-E-1</title></rect>
    <rect class="seat" data-seat-id="right-E-2" x="980" y="250" width="28" height="28"><title>
      right-E-2</title></rect>
    <rect class="seat" data-seat-id="right-E-3" x="1014" y="250" width="28" height="28"><title>
      right-E-3</title></rect>
    <rect class="seat" data-seat-id="right-E-4" x="1048" y="250" width="28" height="28"><title>
      right-E-4</title></rect>
    <rect class="seat" data-seat-id="right-E-5" x="1082" y="250" width="28" height="28"><title>
      right-E-5</title></rect>
    <rect class="seat" data-seat-id="right-E-6" x="1116" y="250" width="28" height="28"><title>
      right-E-6</title></rect>
    <rect class="seat" data-seat-id="right-E-7" x="1150" y="250" width="28" height="28"><title>
      right-E-7</title></rect>
    <text class="row-label" x="28.0" y="298.0">F</text>
    <rect class="seat" data-seat-id="left-F-1" x="44" y="284" width="28" height="28"><title>
      left-F-1</title></rect>
    <rect class="seat" data-seat-id="left-F-2" x="78" y="284" width="28" height="28"><title>
      left-F-2</title></rect>
    <rect class="seat" data-seat-id="left-F-3" x="112" y="284" width="28" height="28"><title>
      left-F-3</title></rect>
    <rect class="seat" data-seat-id="left-F-4" x="146" y="284" width="28" height="28"><title>
      left-F-4</title></rect>
    <rect class="seat" data-seat-id="left-F-5" x="180" y="284" width="28" height="28"><title>
      left-F-5</title></rect>
    <rect class="seat" data-seat-id="left-F-6" x="214" y="284" width="28" height="28"><title>
      left-F-6</title></rect>
    <rect class="seat" data-seat-id="left-F-7" x="248" y="284" width="28" height="28"><title>
      left-F-7</title></rect>
    <rect class="seat" data-seat-id="main-F-1" x="308" y="284" width="28" height="28"><title>
      main-F-1</title></rect>
    <rect class="seat" data-seat-id="main-F-2" x="342" y="284" width="28" height="28"><title>
      main-F-2</title></rect>
    <rect class="seat" data-seat-id="main-F-3" x="376" y="284" width="28" height="28"><title>
      main-F-3</title></rect>
    <rect class="seat" data-seat-id="main-F-4" x="410" y="284" width="28" height="28"><title>
      main-F-4</title></rect>
    <rect class="seat" data-seat-id="main-F-5" x="444" y="284" width="28" height="28"><title>
      main-F-5</title></rect>
    <rect class="seat" data-seat-id="main-F-6" x="478" y="284" width="28" height="28"><title>
      main-F-6</title></rect>
    <rect class="seat" data-seat-id="main-F-7" x="512" y="284" width="28" height="28"><title>
      main-F-7</title></rect>
    <rect class="seat" data-seat-id="main-F-8" x="546" y="284" width="28" height="28"><title>
      main-F-8</title></rect>
    <rect class="seat" data-seat-id="main-F-9" x="580" y="284" width="28" height="28"><title>
      main-F-9</title></rect>
    <rect class="seat" data-seat-id="main-F-10" x="614" y="284" width="28" height="28"><title>
      main-F-10</title></rect>
    <rect class="seat" data-seat-id="main-F-11" x="648" y="284" width="28" height="28"><title>
      main-F-11</title></rect>
    <rect class="seat" data-seat-id="main-F-12" x="682" y="284" width="28" height="28"><title>
      main-F-12</title></rect>
    <rect class="seat" data-seat-id="main-F-13" x="716" y="284" width="28" height="28"><title>
      main-F-13</title></rect>
    <rect class="seat" data-seat-id="main-F-14" x="750" y="284" width="28" height="28"><title>
      main-F-14</title></rect>
    <rect class="seat" data-seat-id="main-F-15" x="784" y="284" width="28" height="28"><title>
      main-F-15</title></rect>
    <rect class="seat" data-seat-id="main-F-16" x="818" y="284" width="28" height="28"><title>
      main-F-16</title></rect>
    <rect class="seat" data-seat-id="main-F-17" x="852" y="284" width="28" height="28"><title>
      main-F-17</title></rect>
    <rect class="seat" data-seat-id="main-F-18" x="886" y="284" width="28" height="28"><title>
      main-F-18</title></rect>
    <rect class="seat" data-seat-id="right-F-1" x="946" y="284" width="28" height="28"><title>
      right-F-1</title></rect>
    <rect class="seat" data-seat-id="right-F-2" x="980" y="284" width="28" height="28"><title>
      right-F-2</title></rect>
    <rect class="seat" data-seat-id="right-F-3" x="1014" y="284" width="28" height="28"><title>
      right-F-3</title></rect>
    <rect class="seat" data-seat-id="right-F-4" x="1048" y="284" width="28" height="28"><title>
      right-F-4</title></rect>
    <rect class="seat" data-seat-id="right-F-5" x="1082" y="284" width="28" height="28"><title>
      right-F-5</title></rect>
    <rect class="seat" data-seat-id="right-F-6" x="1116" y="284" width="28" height="28"><title>
      right-F-6</title></rect>
    <rect class="seat" data-seat-id="right-F-7" x="1150" y="284" width="28" height="28"><title>
      right-F-7</title></rect>
    <text class="row-label" x="28.0" y="332.0">G</text>
    <rect class="seat" data-seat-id="left-G-1" x="44" y="318" width="28" height="28"><title>
      left-G-1</title></rect>
    <rect class="seat" data-seat-id="left-G-2" x="78" y="318" width="28" height="28"><title>
      left-G-2</title></rect>
    <rect class="seat" data-seat-id="left-G-3" x="112" y="318" width="28" height="28"><title>
      left-G-3</title></rect>
    <rect class="seat" data-seat-id="left-G-4" x="146" y="318" width="28" height="28"><title>
      left-G-4</title></rect>
    <rect class="seat" data-seat-id="left-G-5" x="180" y="318" width="28" height="28"><title>
      left-G-5</title></rect>
    <rect class="seat" data-seat-id="left-G-6" x="214" y="318" width="28" height="28"><title>
      left-G-6</title></rect>
    <rect class="seat" data-seat-id="left-G-7" x="248" y="318" width="28" height="28"><title>
      left-G-7</title></rect>
    <rect class="seat" data-seat-id="main-G-1" x="308" y="318" width="28" height="28"><title>
      main-G-1</title></rect>
    <rect class="seat" data-seat-id="main-G-2" x="342" y="318" width="28" height="28"><title>
      main-G-2</title></rect>
    <rect class="seat" data-seat-id="main-G-3" x="376" y="318" width="28" height="28"><title>
      main-G-3</title></rect>
    <rect class="seat" data-seat-id="main-G-4" x="410" y="318" width="28" height="28"><title>
      main-G-4</title></rect>
    <rect class="seat" data-seat-id="main-G-5" x="444" y="318" width="28" height="28"><title>
      main-G-5</title></rect>
    <rect class="seat" data-seat-id="main-G-6" x="478" y="318" width="28" height="28"><title>
      main-G-6</title></rect>
    <rect class="seat" data-seat-id="main-G-7" x="512" y="318" width="28" height="28"><title>
      main-G-7</title></rect>
    <rect class="seat" data-seat-id="main-G-8" x="546" y="318" width="28" height="28"><title>
      main-G-8</title></rect>
    <rect class="seat" data-seat-id="main-G-9" x="580" y="318" width="28" height="28"><title>
      main-G-9</title></rect>
    <rect class="seat" data-seat-id="main-G-10" x="614" y="318" width="28" height="28"><title>
      main-G-10</title></rect>
    <rect class="seat" data-seat-id="main-G-11" x="648" y="318" width="28" height="28"><title>
      main-G-11</title></rect>
    <rect class="seat" data-seat-id="main-G-12" x="682" y="318" width="28" height="28"><title>
      main-G-12</title></rect>
    <rect class="seat" data-seat-id="main-G-13" x="716" y="318" width="28" height="28"><title>
      main-G-13</title></rect>
    <rect class="seat" data-seat-id="main-G-14" x="750" y="318" width="28" height="28"><title>
      main-G-14</title></rect>
    <rect class="seat" data-seat-id="main-G-15" x="784" y="318" width="28" height="28"><title>
      main-G-15</title></rect>
    <rect class="seat" data-seat-id="main-G-16" x="818" y="318" width="28" height="28"><title>
      main-G-16</title></rect>
    <rect class="seat" data-seat-id="main-G-17" x="852" y="318" width="28" height="28"><title>
      main-G-17</title></rect>
    <rect class="seat" data-seat-id="main-G-18" x="886" y="318" width="28" height="28"><title>
      main-G-18</title></rect>
    <rect class="seat" data-seat-id="right-G-1" x="946" y="318" width="28" height="28"><title>
      right-G-1</title></rect>
    <rect class="seat" data-seat-id="right-G-2" x="980" y="318" width="28" height="28"><title>
      right-G-2</title></rect>
    <rect class="seat" data-seat-id="right-G-3" x="1014" y="318" width="28" height="28"><title>
      right-G-3</title></rect>
    <rect class="seat" data-seat-id="right-G-4" x="1048" y="318" width="28" height="28"><title>
      right-G-4</title></rect>
    <rect class="seat" data-seat-id="right-G-5" x="1082" y="318" width="28" height="28"><title>
      right-G-5</title></rect>
    <rect class="seat" data-seat-id="right-G-6" x="1116" y="318" width="28" height="28"><title>
      right-G-6</title></rect>
    <rect class="seat" data-seat-id="right-G-7" x="1150" y="318" width="28" height="28"><title>
      right-G-7</title></rect>
  </svg>
</template>

<style scoped lang="scss">
svg {
  width: 100%;
  height: auto;
}

.seat {
  rx: 4;
  ry: 4;
  fill: #4a90d9;
  stroke: #2c5f8a;
  stroke-width: 1;
  cursor: pointer;

  &.unavailable {
    pointer-events: none;
    cursor: not-allowed;
    fill: #555555;
  }

  &.selected {
    fill: #7c0d0e;
  }

  &:hover {
    fill: #f0a500;
  }
}

.row-label {
  font: bold 13px sans-serif;
  fill: #555;
  dominant-baseline: middle;
  text-anchor: middle;
}

.section-label {
  font: bold 12px sans-serif;
  fill: #888;
  text-anchor: middle;
  letter-spacing: 1px;
}

.stage {
  rx: 6;
  ry: 6;
  fill: #222;
}

.stage-label {
  font: bold 14px sans-serif;
  fill: #fff;
  text-anchor: middle;
  dominant-baseline: middle;
}
</style>
