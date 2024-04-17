<template>
    <div class="room-container">
        <RoomHeader />
        <slot></slot>
    </div>
    
</template>

<script>
import RoomHeader from '@/components/RoomHeader.vue';
export default {
    components : {
        RoomHeader,
      },
}
</script>

<style>
.room-container{
  display: flex;
  flex-direction: column;
  height: 100vh;
}
</style>