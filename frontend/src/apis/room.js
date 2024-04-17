import axios from './config';

export default {
    getRooms() {
        return axios.get("/room",
            {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': localStorage.getItem('token')
                }
            });
    },
    getRoomById(roomId) {
        return axios.get(`/room/${roomId}`,
            {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': localStorage.getItem('token')
                }
            });
    },
    patchRoomById(roomId, data) {
        return axios.patch(`/room/${roomId}`, data,
            {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': localStorage.getItem('token')
                }
            }
        );
    },
    getSearchPosts(roomId, keyword, category) {
        return axios.get(`/room/${roomId}/search`, keyword, category,
            {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': localStorage.getItem('token')
                }
            }
        );
    }
};