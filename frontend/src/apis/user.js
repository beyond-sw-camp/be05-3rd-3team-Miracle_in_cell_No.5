import axios from './config' ;
export default {
    getLoginUserProfile() {
      return axios.get(`/users/profile`);
    },
    patchProfile(data) {
      return axios.patch(`/users/profile/nickname`,data);
    },
    patchProfileImage(data) {
      return axios.patch(`/users/profile/image`,data);
    },
    patchPassword(data) {
      return axios.patch(`/users/profile/password`,data);
    },
    getPosts() {
      return axios.get(`/users/posts`);
    }
};