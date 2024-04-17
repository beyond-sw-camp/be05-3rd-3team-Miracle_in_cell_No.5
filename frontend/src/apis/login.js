import axios from './config';

export default {
    postLogin(data) {
        return axios.post('/login', data);
    }
};