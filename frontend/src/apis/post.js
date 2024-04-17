import axios from './config';

export default {
    savePostById(roomId, data) {
        return axios.post(`/posts/${roomId}`, data, {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': localStorage.getItem('token')
                }
            }
        );
    },
    patchPostById(postId, data) {
        return axios.patch(`/posts/${postId}`, data, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': localStorage.getItem('token')
            }
        });
    },
    deletePostById(postId) {
        return axios.delete(`/posts/${postId}`, {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': localStorage.getItem('token')
                }
            }
        );
    },
    getPostById(postId) {
        return axios.get(`/posts/${postId}`, {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': localStorage.getItem('token')
                }
            }
        );
    },
    getPostsByRoomId(roomId, keyword, category) {
        return axios.get(`/room/${roomId}/search`, keyword, category, {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': localStorage.getItem('token')
                }
            }
        );
    }
};