<template>
    <component :is="$route.meta.layout || 'div'">
      <router-view></router-view>
    </component>
</template>
  
<script>
// import { onMounted } from 'vue';

  export default {
    setup() {
      // const isAuth = () => {
      //   return localStorage.getItem("jwtToken") ;
      // }
      // onMounted(()=>{
      //   console.log("onmounted : ",isAuth())
      //   if(!isAuth()){
      //     // this.$router.push('/login');
      //     window.location.href = '/login';
      //   }
      // })
      
    }
  }
</script>
  
<style>
/* .mainpage{
  display: flex;
  flex-direction: column;
  height: 100vh;
  background-color: #f5f5f5;
} */
</style>