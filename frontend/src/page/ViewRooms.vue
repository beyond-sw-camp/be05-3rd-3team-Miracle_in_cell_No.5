<template>
  <div id="main-container" class="text-center">
    <div class="speaker">
      <router-link to="/speaker"><img class="speaker-img" src="../img/speaker.png"/></router-link>
      <div class="speaker-content">
        <p>{{ speakerContent }}</p>
        <div class="board-tools">
          <img class="pen" src="@/img/pen.png">
          <img class="eraser" src="@/img/es.png">
        </div>
      </div>

    </div>

    <div class="rooms-container">
      <div v-for="(room) in roomList" :key="room.id">
        <RoomCard :room="room"/>
      </div>

    </div>

  </div>
</template>

<script>
import myRoomsApi from '@/apis/room';
import {ref} from 'vue'
import '../css/Main.css';
import RoomCard from '@/components/RoomCard.vue';
import speakerApi from '@/apis/speaker';

export default {
  components: {
    RoomCard
  },
  setup() {
    const roomList = ref([]);
    const speakerContent = ref('');

    const getSpeakerContent = async () => {
      try {
        const response = await speakerApi.getFindNowSpeaker();
        speakerContent.value = response.data.data.content;
        console.log("speakerContent: ", speakerContent.value)
      } catch (error) {
        console.error("Error fetching scraps:", error);
      }
    }
    getSpeakerContent();

    const getRoom = async () => {
      try {
        const response = await myRoomsApi.getRooms();
        console.log(response.data.data);
        roomList.value = response.data.data;
      } catch (error) {
        console.error("Error viewrooms : ", error);
      }
    }
    getRoom();

    return {
      roomList,
      speakerContent,
      getSpeakerContent
    }
  }
}
</script>

<style>
.main-container {
  margin: auto;
}
</style>