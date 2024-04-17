import axios from './config' ;

export default {
    getFindCommentsByPostId(postId) {
        return axios.get(`/comments/${postId}`);
    },
    postComment(postId,data) {
        return axios.post(`/comments/${postId}`,data) ;
    },
    deleteComment(commentId){
        return axios.delete(`/comments/${commentId}`) ;
    }
}