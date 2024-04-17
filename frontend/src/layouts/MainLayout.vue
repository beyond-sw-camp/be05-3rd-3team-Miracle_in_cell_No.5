<template>
    <div class="mainpage">
        <MainHeader />
        <slot></slot>
        <AppFooter />
    </div>
</template>

<script>
import MainHeader from '@/components/MainHeader.vue';
import AppFooter from '@/components/AppFooter.vue';

export default {
    components : {
        MainHeader,
        AppFooter,
      },
}
</script>

<style>
.mainpage{
  display: flex;
  flex-direction: column;
  height: 100vh;
  background-color: #f5f5f5;
}
</style>