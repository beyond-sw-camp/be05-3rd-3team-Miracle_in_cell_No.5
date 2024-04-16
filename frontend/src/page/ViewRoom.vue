<template>
    <div class='roomInfo-container'>
      <div class='roomInfo'>
        <img src="/img/Vector.png" class='room-profile'/>
        <div class='myroom'>
          <h1 class='roomName'>{{room&&room.roomName}}</h1>
          <div class='roomIntroduction'>{{room&&room.introduction}}</div>
          <div class='ismyroom'>
            <router-link :to="`/room/${roomId}/editroom`"><button class='editpost'>수정하기</button></router-link>
          </div>
        </div>

        <div class='ismyroom-container'>
          <div class='sol ismyroom'>
            <img src="/img/sol.png" class="sol-img"/><strong>
            <span>솔방울 개수</span></strong>
          </div>
          <div class='write-con isnotmyroom'>
            <a><button class='write-btn'>글쓰기</button></a>
          </div>
        </div>
        
        <a><button class='viewpost-btn'>글 더보기...</button></a>
      </div>
    </div>
</template>

<script>
import { useRoute } from 'vue-router';
// import axios from 'axios';
import myRoomsApi from '@/apis/room' ;
import { ref } from 'vue';
import '@/css/ViewRoom.css';
export default {
  setup() {
    const route = useRoute() ;
    let roomId = route.params.id ;
    console.log("room view",roomId) ;
    const room = ref({}) ;

    const getRoom = async () => {
      try {
        console.log(roomId);
        const response = await myRoomsApi.getRoomById(roomId);
        console.log(response.data) ;
        room.value = response.data ;
      } catch (error) {
          console.error("Error fetching scraps:", error);
      }
    }
    getRoom() ;

    return {
      room,
      roomId
    }
  }
}
</script>

<style>
.sol-img{
  width: 20;
  height: 20;
}
</style>