<template>
  <div className='roomInfo-container'>
            <div className='roomInfo'>
                <div class="edit-container">
                    <form @submit.prevent="onSubmit" class="room-form">
                        <div class="room-inputs">
                            <label>방 이름</label>
                            <input type="text" id="roomName" name="roomName"
                                v-model="room.roomName" maxlength="11">

                            <label for="introduction">방 소개</label>
                            <textarea rows="5" id="introduction" name="introduction" 
                                v-model="room.introduction" class="fixed-size-textarea"/>
                        </div>
                        <button className="edit-btn">수정하기</button>
                    </form>
                </div>
            </div>
                
        </div>
</template>

<script>
import myRoomsApi from '@/apis/room' ;
import { ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

export default {
    setup(){
        const route = useRoute() ;
        const router = useRouter() ;
        const roomId = route.params.id ;
        const room = ref({
            roomName : "",
            introduction : "" 
        }) ;

        const getRoom = async () =>{
            try{
                const response = await myRoomsApi.getRoomById(roomId) ;
                console.log("edit room getRoom res ," ,response.data.data) ;
                room.value = { ...response.data.data };  
            }catch(e){
                console.log("edit room getRoom error, ",e) ;
            }
        }
        getRoom() ;

        const onSubmit = async () =>{
            try{
                const response = await myRoomsApi.patchRoomById(roomId,{
                    roomName : room.value.roomName,
                    introduction : room.value.introduction 
                }) ;
                console.log("edit room putRoom res ," ,response.data.data) ;
                room.value= response.data.data ;
                router.go(-1) ;
            }catch(e){
                console.log("edit room putRoom error, ",e)
            }
        }
        return{
            room,
            onSubmit,

        }
    }

}
</script>

<style scoped>

.edit-container{
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
}
.room-form {
    display: flex;
    width: 800px;
    align-items: flex-end;
}
.room-inputs{
    height: 100%;
    width: 100%;
    display: flex;
    flex-direction: column;
}
form input , textarea{
    display: block;
    margin-bottom: 20px;
    border: 1px solid #E5E9F5;
    background-color: #F6F7FA;
    padding: 20px;
    margin-top: 10px;
    border-radius: 10px;
    width: 70%;
}
.fixed-size-textarea {
  resize: none; /* 리사이즈 비활성화 */
  width: 70%; /* 원하는 너비로 지정 */
  height: 150px; /* 원하는 높이로 지정 */
}
label{
    color: #E5E9F5;
}
.edit-btn {
    border: 0;
    background-color: white;
    width: 200px;
    height: 81px;
    border-radius: 20px;
    margin-bottom: 40px;
    &:hover {
        animation: jelly 0.5s;
    }
}

</style>