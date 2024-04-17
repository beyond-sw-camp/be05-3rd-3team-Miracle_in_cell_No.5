import axios from './config' ;

export default {
    savePostById(roomId,data) {
        return axios.post(`/posts/${roomId}`, data);
    },
    patchPostById(postId,data) {
        return axios.patch(`/posts/${postId}`,data) ;
    },
    deletePostById(postId){
        return axios.delete(`/posts/${postId}`) ;
    },
    getPostById(postId){
        return axios.get(`/posts/${postId}`) ;
    },
    getPostsByRoomId(roomId,keyword,category){
        return axios.get(`/room/${roomId}/search`,keyword,category);
    }
};