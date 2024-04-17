<template>
    <div class="container">
      <div class="card">
        <div class="content">
          <div class="img"><img src="img/base.png"></div>
          <div class="cardcontent">
            <h3>{{ truncate(room.roomName, 11) }}<br><span>{{ truncate(room.introduction, 15) }}</span></h3>
          </div>
        </div>
        <div class="sci">
          <router-link :to="`/room/${room.id}`" class="btn goroom" aria-hidden="true">방 더보기</router-link>
          <!-- <li style="--i:1">
            <a href="#"><button class="fa fa-facebook" aria-hidden="true"></button></a>
          </li> -->
        </div>
      </div>
    </div>
</template>
  
<script>
  import '@/css/RoomCard.css' ;
  export default {
      props: {
          room: {
              type: Object,
              required: true 
          }
      },
      setup() {
        const truncate = (text, maxLength) => {
          return text.length > maxLength ? text.slice(0, maxLength) + '...' : text;
        };
  
        return {
          truncate
        };
      }
  }
</script>
  
<style>
  
</style>
  