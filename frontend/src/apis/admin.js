import axios from './config';

export default {
    getViewAllPosts() {
        return axios.get('/admin', {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': localStorage.getItem('token')
            }
        });
    },
    deletePost(postId) {
        return axios.post('/admin/${postId}', {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': localStorage.getItem('token')
            }
        });
    }
}