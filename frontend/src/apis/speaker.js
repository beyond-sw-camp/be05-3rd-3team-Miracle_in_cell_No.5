import axios from './config' ;

export default {
    postSaveSpeaker(data) {
        return axios.post('/speakers', data);
    },
    getFindSpeaker(reservationDate) {
        return axios.get('/speakers/date', reservationDate);
    },
    getFindAll() {
        return axios.get('/speakers');
    },
    getFindNowSpeaker() {
        return axios.get('/speakers/now');
    }
};