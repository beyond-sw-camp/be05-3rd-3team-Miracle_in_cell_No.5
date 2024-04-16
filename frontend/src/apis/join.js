import axios from './config' ;

export default {
    postJoin(data) {
        return axios.post('/join', data);
    },
    postSendMail(data) {
        return axios.post('/join/mail/send',data) ;
    },
    postSendMailForPasswordReset(data){
        return axios.post('/join/mail/send/password-reset',data) ;
    },
    postMailCheck(data){
        return axios.post('/join/mail/check',data) ;
    },
    getIsExistsByLoginId(data){
        return axios.get('/join/loginId/check',data) ;
    },
    patchPassworReset(data){
        return axios.patch('/join/password-reset',data) ;
    }
};