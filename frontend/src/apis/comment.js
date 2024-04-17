import axios from './config';

export default {
    getFindCommentsByPostId(postId) {
        return axios.get(`/comments/${postId}`, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': localStorage.getItem('token')
            }
        });
    },
    postComment(postId, data) {
        return axios.post(`/comments/${postId}`, data, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': localStorage.getItem('token')
            }
        });
    },
    deleteComment(commentId) {
        return axios.delete(`/comments/${commentId}`, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': localStorage.getItem('token')
            }
        });
    }
}