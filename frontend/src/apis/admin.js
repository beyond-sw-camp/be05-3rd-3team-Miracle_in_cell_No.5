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
        return axios.delete(`/admin/${postId}`, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': localStorage.getItem('token')
            }
        });
    }
}