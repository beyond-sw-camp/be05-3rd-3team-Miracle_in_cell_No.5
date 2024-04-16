import axios from 'axios';

axios.defaults.baseURL = 'http://localhost:8080';
axios.defaults.xsrfCookieName = 'csrftoken';
axios.defaults.xsrfHeaderName = 'X-CSRFToken';

export default axios;