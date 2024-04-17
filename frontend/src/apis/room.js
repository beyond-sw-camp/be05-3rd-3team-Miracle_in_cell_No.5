import axios from './config' ;
export default {
    postRoom(data) {
      return axios.post('/room/save', data);
    },
    getRooms() {
      return axios.get("/room/list") ;
    },
    getRoomById(roomId){
      return axios.get(`/room/${roomId}/view`) ;
    },
    patchRoomById(roomId,data){
      return axios.patch(`/room/${roomId}/edit`,data) ;
    }
};