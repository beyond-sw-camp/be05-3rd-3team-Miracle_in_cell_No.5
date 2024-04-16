<template>
  <nav class="nav-col navbar navbar-expand-lg">
    <div class="container-fluid">
      <div @click="()=>{this.$router.go(-1)}"><img src='\img\arrow.png' class='backarrow'/></div>
      <router-link to="/"> <img src='\img\logo-white.png' class='logo'/></router-link>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarTogglerDemo02" aria-controls="navbarTogglerDemo02" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="menu" id="navbarTogglerDemo02">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
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
  </nav>
</template>

<script>
import '../css/Header.css';
export default {

}
</script>

<style>

</style>