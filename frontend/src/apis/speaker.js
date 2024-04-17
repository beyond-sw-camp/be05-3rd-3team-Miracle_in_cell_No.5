import axios from './config';

export default {
    postSaveSpeaker(data) {
        return axios.post('/speakers', data,
            {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': localStorage.getItem('token')
                }
            }
        );
    },
    getFindSpeaker(reservationDate) {
        return axios.get('/speakers/date', reservationDate, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': localStorage.getItem('token')
            }
        });
    },
    getFindAll() {
        return axios.get('/speakers', {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': localStorage.getItem('token')
            }
        });
    },
    getFindNowSpeaker() {
        return axios.get('/speakers/now', {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': localStorage.getItem('token')
            }
        });
    }
};