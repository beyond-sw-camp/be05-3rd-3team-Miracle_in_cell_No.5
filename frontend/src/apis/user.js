import axios from './config' ;
export default {
    getUser(id) {
      return axios.post(`/users/${id}`);
    }
};