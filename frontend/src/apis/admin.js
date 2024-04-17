import axios from './config' ;

export default {
    getViewAllPosts() {
        return axios.get('/admin');
    },
    deletePost(postId) {
        return axios.post('/admin/${postId}') ;
    }
}