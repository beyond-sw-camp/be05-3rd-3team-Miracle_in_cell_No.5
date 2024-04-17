import axios from './config' ;
export default {
    getRooms() {
      return axios.get("/room") ;
    },
    getRoomById(roomId){
      return axios.get(`/room/${roomId}`) ;
    },
    patchRoomById(roomId,data){
      return axios.patch(`/room/${roomId}`,data) ;
    }
};