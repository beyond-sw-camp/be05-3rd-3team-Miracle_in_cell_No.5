<template>
    <MyPageHeader/>
    <div class="d-flex" style="height: 100vh;">
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
  </template>
  
  <script>
  import MyPageHeader from './MyPageHeader.vue';
  import MyPageSidebar from './MyPageSidebar.vue';
  
  export default {
    components: {
      MyPageSidebar,
        MyPageHeader
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
  