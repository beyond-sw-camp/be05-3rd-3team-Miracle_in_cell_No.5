import axios from './config';

export default {
    getUserProfile() {
        return axios.get(`/users/profile`, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': localStorage.getItem('token')
            }
        });
    },
    patchProfile(data) {
        return axios.patch(`/users/profile/nickname`, data, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': localStorage.getItem('token')
            }
        });
    },
    patchProfileImage(data) {
        return axios.patch(`/users/profile/image`, data, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': localStorage.getItem('token')
            }
        });
    },
    patchPassword(data) {
        return axios.patch(`/users/profile/password`, data, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': localStorage.getItem('token')
            }
        });
    },
    getUserPosts() {
        return axios.get(`/users/posts`, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': localStorage.getItem('token')
            }
        });
    }
};