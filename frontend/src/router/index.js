import { createRouter ,createWebHistory } from 'vue-router' ;
import Main  from '../page/MainPage.vue' ; 
import Room  from '../page/RoomPage.vue' ; 
import ViewRoom  from '../page/ViewRoom.vue' ; 
import ViewRooms  from '../page/ViewRooms.vue' ; 
import CreateRoom  from '../page/CreateRoom.vue' ; 
import EditRoom  from '../page/EditRoom.vue' ; 

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
                    path :"createroom",
                    component: CreateRoom,
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