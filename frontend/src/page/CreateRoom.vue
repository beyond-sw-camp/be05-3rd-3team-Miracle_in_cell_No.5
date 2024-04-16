<template>
  <div>
        <form @submit.prevent="postRoom">
            <label>방 이름</label>
            <input name='roomName'
                   id='roomName'
                   v-model="room.roomName"
                   @change="onChange"
                   type='text'
                   placeholder='방 이름 입력하세요'/>
            <label>방 소개</label>
            <input name='introduction'
                   id='introduction'
                   v-model="room.introduction"
                   type='text' 
                   placeholder='방 소개글 입력하세요'/>
            <button type='submit'>방 만들기</button>
        </form>
    </div>
</template>

<script>
import { ref } from 'vue'
import myRoomsApi from '@/apis/room' ;
export default {
    setup(){
        const room = ref({
            roomName : "",
            introduction : "" 
        }) ;
        const onChange = () =>{
            console.log(room.value.roomName) ;
        }
        const postRoom = async () =>{
            try {
                const res = await myRoomsApi.postRoom(room.value);
                console.log("성공적으로 보냈습니다.",res.data);
            } catch (error) {
                console.log("error post room ",error) ;
            }
        } 
        return{
            postRoom,
            onChange,
            room
        }
    }
}
</script>

<style>

</style>