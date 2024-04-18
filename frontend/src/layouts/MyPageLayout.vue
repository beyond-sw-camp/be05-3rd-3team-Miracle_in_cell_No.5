<template>
  <div class="vh-100">
    <MyPageHeader/>
    <div class="d-flex" >
      <MyPageSidebar />
      <div class="flex-grow-1 container-fluid p-0">
        <!-- Content Header -->
        <div class="content-header">
          My Page
        </div>
        <!-- Main Content -->
        <router-view/>
      </div>
  </div>
  <AppFooter/>
    
  </div>
</template>
  
  <script>
  import AppFooter from '@/components/AppFooter.vue';
import MyPageHeader from '@/components/MyPageHeader.vue';
  import MyPageSidebar from '@/components/MyPageSidebar.vue';
  
  export default {
    components: {
    MyPageSidebar,
    MyPageHeader,
    AppFooter
}
  };
  </script>
  
  <style scoped>
  .content-header {
    background-color: #5F73B0; 
    font-size: 45px;
    color: white; 
    text-align: center;
    padding: 20px 0;
    text-decoration-line: underline;
  }
  </style>
  