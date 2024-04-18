<template>
  <div class='roomInfo-container'>
    <div class='roomInfo'>
      <img :src="`http://localhost:8080/images/${room.profileImage}`" class='room-profile '/>
      <div class='myroom'>
        <h1 class='roomName'>{{ room && room.roomName }}</h1>
        <div class='roomIntroduction'>{{ room && room.introduction }}</div>
        <div class='ismyroom' v-if="isUserRoom()">
          <router-link :to="`/room/${roomId}/editroom`">
            <button class='edit-btn'>수정하기</button>
          </router-link>
        </div>
      </div>

      <div class='ismyroom-container'>
        <div class='sol ismyroom' v-if="isUserRoom()">
          <img src="/img/sol.png" class="sol-img"/><strong>
          <span>솔방울 개수</span></strong>
        </div>
        <div class='write-con isnotmyroom' v-if="!isUserRoom()">
          <!-- <a><button class='write-btn'>글쓰기</button></a> -->
          <router-link :to="`/room/${roomId}/submitpost`">
            <button class="write-btn">글쓰기</button>
          </router-link>
        </div>
      </div>

      <!-- <a><button class='viewpost-btn'>글 더보기...</button></a> -->
      <router-link :to="`/room/${roomId}/viewposts`">
        <button class="viewpost-btn">글 더보기...</button>
      </router-link>
    </div>
  </div>
</template>

<script>
import {useRoute} from 'vue-router';
// import axios from 'axios';
import myRoomsApi from '@/apis/room';
import myUsersApi from '@/apis/user';
import {ref} from 'vue';
import '@/css/ViewRoom.css';

export default {
  setup() {
    const route = useRoute();
    let roomId = route.params.id;
    console.log("room view", roomId);
    const room = ref({});
    const user = ref({});

    const isUserRoom = () => {
      return roomId == user.value.roomId;
    }

    const getRoom = async () => {
      try {
        console.log(roomId);
        const response = await myRoomsApi.getRoomById(roomId);
        console.log(response.data.data);
        room.value = response.data.data;
      } catch (error) {
        console.error("Error ViewRoom getRoom : ", error);
      }
    }
    getRoom();

    const getUser = async () => {
      try {
        const response = await myUsersApi.getUserProfile();
        console.log(response.data.data);
        user.value = response.data.data;
      } catch (error) {
        console.error("Error ViewRoom getUser : ", error);
      }
    }
    getUser();

    return {
      room,
      roomId,
      isUserRoom,
    }
  }
}
</script>

<style>
.sol-img {
  width: 20;
  height: 20;
}
</style>