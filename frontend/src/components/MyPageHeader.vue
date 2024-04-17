<template>
    <div>
      <nav class="nav-col navbar navbar-expand-lg">
        <div class="container-fluid">
          <div @click="()=>{this.$router.go(-1)}"><img src='\img\arrow.png' class='backarrow'/></div>
          <router-link to="/"> <img src='\img\logo-white.png' class='logo'/></router-link>
          <button class="navbar-toggler" type="button" data-bs-toggle="collapse" 
                  data-bs-target="#navbarTogglerDemo02" aria-controls="navbarTogglerDemo02" 
                  aria-expanded="false" aria-label="Toggle navigation" @click="toggleMenu">
            <span class="navbar-toggler-icon"></span>
          </button>
          <div class="menu" id="navbarTogglerDemo02">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
              <li class="nav-item">
                <a class="nav-link" aria-current="page" href="../admin">관리자페이지</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="../mypage/my-info">마이페이지</a>
              </li>
              <li class="nav-item">
                <a class="nav-link">로그아웃</a>
              </li>
            </ul>
          </div>
        </div>
      </nav>
      <!-- 모바일  ver. 토글러 눌렀을 때,,-->
      <div v-if="toggle" class="menu-mobile" id="navbarTogglerDemo02" >
          <ul class="navbar-nav me-auto">
            <li class="nav-item">
              <a class="nav-link" aria-current="page" href="#">관리자페이지</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#">마이페이지</a>
            </li>
            <li class="nav-item">
              <a class="nav-link">로그아웃</a>
            </li>
          </ul>
      </div>
    </div>
    
  </template>
  
  <script>
  import '../css/Header.css';
  import { ref } from 'vue';
  export default {
    setup(){
      const toggle = ref(false) ;
      const toggleMenu = () =>{
        toggle.value = !toggle.value ;
      }
      return{
        toggleMenu,
        toggle
      }
    }
  }
  </script>
  
  <style>
  
  </style>