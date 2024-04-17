import { createRouter ,createWebHistory } from 'vue-router' ;
import Main  from '../page/MainPage.vue' ; 
import Room  from '../page/RoomPage.vue' ; 
import ViewRoom  from '../page/ViewRoom.vue' ; 
import ViewRooms  from '../page/ViewRooms.vue' ; 
import EditRoom  from '../page/EditRoom.vue' ; 
import Speaker from '../page/SpeakerReservation.vue' ;

const router = createRouter({
    history: createWebHistory(),
    routes: [
        {
            path: "/",
            component : Main,
            children : [
                {
                    path: "",
                    component : ViewRooms,
                },
                {
                    path : "speaker",
                    component : Speaker
                }
            ]
        },
        {
            path : "/room/:id/",
            component : Room,
            children : [
                {
                    path : "",
                    component : ViewRoom,
                },
                {
                    path : "editroom",
                    component : EditRoom,
                },
            ]
        },
    ],
});

export default router ;