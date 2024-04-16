<template>
  <div class="room-container">
    <RoomHeader />
    <router-view></router-view>
    <!-- <AppFooter /> -->
  </div>
</template>

<script>
import RoomHeader from '@/components/RoomHeader.vue';
// import AppFooter from '@/components/AppFooter.vue';
import { useRoute } from 'vue-router';

export default {
  components : {
    RoomHeader,
    // AppFooter,
  },
  setup() {
    const route = useRoute() ;
    console.log("room page",route.params.id);

  }

}
</script>

<style>
.room-container{
  display: flex;
  flex-direction: column;
  height: 100vh;
}
</style>