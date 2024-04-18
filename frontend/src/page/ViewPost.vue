<template>
    <div class="roomInfo-container">
        <div class="roomInfo">
            <br><br><br><br>
            <div class="show-post">
                <div class="form-row">
                    <div class="form-group">
                        <!-- <h2>제목</h2>
                        <div v-if="post" class="view-post">
                            <h3>{{ post.title }}</h3>
                        </div> -->
                        <h2><span v-if="post">{{ post.title }}</span></h2>
                    </div>
                    <br>
                    <div class="form-group">
                        <h5>작성자: <span v-if="post">{{ post.authorNickname }}</span>&nbsp;&nbsp;
                            카테고리: <span v-if="post">{{ post.category }}</span>&nbsp;&nbsp;
                            조회수: <span v-if="post">{{ post.viewCount }}</span>&nbsp;&nbsp;
                            작성일: <span v-if="post">{{ post.createdDateTime }}</span></h5>
                    </div>
                    <br>
                    <div class="form-group">
                        <div v-if="post" class="view-post">
                            <h3>{{ post.content }}</h3>
                        </div>
                    </div>
                    <!-- <div class='ismypost' v-if="isUserPost(post.authorNickname)">
                        <router-link :to="`/posts/${post.id}/editpost`"><button class='edit-btn'>수정</button></router-link>
                    </div> -->
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import postApi from '@/apis/post';
import { ref } from 'vue';
import { useRoute } from 'vue-router';
// import room from '@/apis/room';
// import userApi from '@/apis/user';

export default {
    setup() {
        const route = useRoute();
        const postId = route.params.id;
        console.log("postId: ", postId);
        const post = ref(null);

        // const isUserPost = (authorNickname) => {
        //     console.log("nickname", authorNickname);
        //     return authorNickname === userApi.getUserProfile().authorNickname;
        // }

        const showPost = async () => {
            try {
                console.log(postId);
                const response = await postApi.getPostById(postId);
                console.log("response data: ", response.data);
                console.log("response data data: ", response.data.data);
                post.value = response.data.data;
                console.log("content [0] " , post.value[0]);
            } catch (error) {
                console.error("Error fetching scraps:", error);
            }
        }
        showPost();

        return {
            // isUserPost,
            post
        }
    }
};
</script>

<style scoped>
.view-post {
    padding: 20px;
    background-color: #f4f4f4;
    border-radius: 10px;
}

.content {
    margin-top: 20px;
}

.show-post {
    background-color: #5F73B0;
    padding: 20px;
    border-radius: 10px;
    flex-direction: column;
    margin: 0;
    margin-bottom: 50px;
    padding: 0 400px;
    color: white;
}

.show-post .form-group .view-post h3 {
    color: black; /* 내용을 출력하는 글자를 검정색으로 변경 */
}
</style>