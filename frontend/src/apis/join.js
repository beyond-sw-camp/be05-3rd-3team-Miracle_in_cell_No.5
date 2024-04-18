import axios from './config';

export default {
    postJoin(data) {
        return axios.post('/join', data, {
            headers: {
                'Content-Type': 'multipart/form-data'
            },
        });
    },
    postSendMail(data) {
        return axios.post('/join/mail/send', data);
    },
    postSendMailForPasswordReset(data) {
        return axios.post('/join/mail/send/password-reset', data);
    },
    postMailCheck(data) {
        return axios.post('/join/mail/check', data, {
            withCredentials: true
        });
    },
    getIsExistsByLoginId(data) {
        return axios.get('/join/loginId/check', data);
    },
    patchPasswordReset(data) {
        return axios.patch('/join/password-reset', data);
    }
};