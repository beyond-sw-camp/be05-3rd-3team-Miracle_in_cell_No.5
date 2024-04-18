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
                        <h2>제목: <span v-if="post">{{ post.title }}</span></h2>
                    </div>
                    <div class="form-group">
                        <h3>작성자: <span v-if="post">{{ post.authorNickname }}</span>카테고리: <span v-if="post">{{ post.category }}</span>조회수: <span v-if="post">{{ post.viewCount }}</span>작성일: <span v-if="post">{{ post.createdDateTime }}</span></h3>
                    </div>
                    <div class="form-group">
                        <div v-if="post" class="view-post">
                            <h3>{{ post.content }}</h3>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import postApi from '@/apis/post';
import { ref } from 'vue';
import { useRoute } from 'vue-router';

export default {
    setup() {
        const route = useRoute();
        const postId = route.params.id;
        console.log("postId: ", postId);
        const post = ref(null);

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
</style>