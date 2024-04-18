import {createRouter, createWebHistory} from 'vue-router';
import Main from '../page/MainPage.vue';
import Room from '../page/RoomPage.vue';
import ViewRoom from '../page/ViewRoom.vue';
import ViewRooms from '../page/ViewRooms.vue';
import EditRoom from '../page/EditRoom.vue';
import Speaker from '../page/ReservationPage.vue';
import SubmitPost from '../page/SubmitPost.vue';
import LoginPage from '../page/LoginPage.vue';
import MainLayout from '@/layouts/MainLayout.vue';
import ArrowLayout from '@/layouts/ArrowLayout.vue';
import ViewPosts from '@/page/ViewPosts.vue';
import MyPageLayout from '@/components/MyPageLayout.vue';
import MyInfo from '@/components/MyInfo.vue';
import MyPosts from '@/components/MyPosts.vue';
import ChangePassword from '@/components/ChangePassword.vue';
import AdminPage from '../page/AdminPage.vue';
import JoinMailPage from "@/page/JoinMailPage.vue";
import JoinPage from "@/page/JoinPage.vue";


const router = createRouter({
    history: createWebHistory(),
    routes: [
        {
            path: "/",
            component: Main,
            meta: {layout: MainLayout},
            children: [
                {
                    path: "",
                    component: ViewRooms,
                },
                {
                    path: "speaker",
                    component: Speaker
                }
            ]
        },
        {
            path: "/room/:id/",
            component: Room,
            meta: {layout: ArrowLayout},
            children: [
                {
                    path: "",
                    component: ViewRoom,
                },
                {
                    path: "editroom",
                    component: EditRoom,
                },
                {
                    path: "submitpost",
                    component: SubmitPost,
                },
                {
                    path: "viewposts",
                    component: ViewPosts,
                },
            ]
        },
        {
            path: "/login",
            component: LoginPage,
        },
        {
            path: "/join/mail",
            component: JoinMailPage,
        },
        {
            path: "/join/info",
            component: JoinPage,
        },
        {
            path: '/mypage',
            component: MyPageLayout,
            children: [
                {
                    path: 'my-info',
                    name: 'MyInfo',
                    component: MyInfo,
                },
                {
                    path: 'my-posts',
                    name: 'MyPosts',
                    component: MyPosts,
                },
                {
                    path: 'change-password',
                    name: 'ChangePassword',
                    component: ChangePassword,
                }
            ]
        },

        {
            path: '/admin',
            component: AdminPage
        }
    ],
});

export default router;