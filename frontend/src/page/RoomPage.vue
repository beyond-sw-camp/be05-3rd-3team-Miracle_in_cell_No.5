<template>
  <component :is="$route.meta.layout || 'div'">
    <router-view></router-view>
  </component>
</template>

<script>
import { useRoute } from 'vue-router';

export default {
  setup() {
    const route = useRoute() ;
    console.log("room page",route.params.id);

  }

}
</script>

<style>
/* .room-container{
  display: flex;
  flex-direction: column;
  height: 100vh;
} */
</style>