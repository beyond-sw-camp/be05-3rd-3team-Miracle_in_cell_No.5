import axios from 'axios';

axios.defaults.baseURL = 'http://localhost:8083';
axios.defaults.xsrfCookieName = 'csrftoken';
axios.defaults.xsrfHeaderName = 'X-CSRFToken';

export default axios;