<template>
    <div class="roomInfo-container">
        <div class="roomInfo">
            <br><br><br><br>
            <div class="submit-post">
                <div class="form-row">
                    <div class="form-group">
                        <!-- <label for="title">제목</label>
                        <input type="text" id="title" v-model="title" placeholder="제목을 입력하세요."> -->
                        <label>제목</label>
                        <input name="postTitle" id="postTitle" v-model="post.title" type="text" placeholder="제목을 입력하세요."/>
                    </div>
                    <div class="form-group">
                        <!-- <label for="writer">작성자</label>
                        <input type="text" id="writer" v-model="getNickname" disabled> -->
                        <label>작성자</label>
                        <input name="postWriter" id="postWriter" :value="nickname" type="text" disabled>
                    </div>
                </div>
                <br>
                <div class="form-row">
                    <div class="form-group category-buttons">
                        <label>카테고리</label>
                        <div class="category-button-group">
                            <button :class="{ active: post.category === 'FREE' }" @click="setCategory('FREE')">Free</button>
                            <button :class="{ active: post.category === 'COMPLIMENT' }" @click="setCategory('COMPLIMENT')">Compliment</button>
                            <button :class="{ active: post.category === 'COMPLAIN' }" @click="setCategory('COMPLAIN')">Complain</button>
                        </div>
                    </div>
                    <div class="form-group anonymous-buttons">
                        <label>익명 여부</label>
                        <div class="anonymous-button-group">
                            <button :class="{ active: post.anonymous === false }" @click="setAnonymous(false)">닉네임 공개</button>
                            <button :class="{ active: post.anonymous === true }" @click="setAnonymous(true)">닉네임 비공개</button>
                        </div>
                    </div>
                </div>
                <br>
                <div class="form-group">
                    <label>내용</label>
                    <textarea name="postContent" id="postContent" v-model="post.content" placeholder="내용을 입력하세요." rows="7"></textarea>
                </div>
                <br><br>
                <button class="submit-button" @click="submitPost">저장</button>
            </div>
        </div>
    </div>
</template>

<script>
import { ref } from 'vue'
import { useRoute } from 'vue-router';
import userApi from '@/apis/user';
import postApi from '@/apis/post';

export default {
    setup() {
        const route = useRoute();
        const roomId = route.params.id;
        console.log("roomId:", roomId);

        const post = ref({
            title: "",
            writer: "",
            category: "",
            anonymous: "",
            content: ""
        })

        const nickname = ref("");

        const getNickname = async () => {
            try {
                const user = await userApi.getUserProfile();
                console.log("user data: ", user.data);
                nickname.value = user.data.data.nickname;
                console.log("nickname value:", nickname.value);
                post.value.writer = user.data.nickname;
            } catch (error) {
                console.error("Error fetching scraps:", error);
            }
        };

        getNickname()

        const submitPost = async () => {
            try {
                const data = {
                    title: post.value.title,
                    writer: post.value.writer,
                    category: post.value.category,
                    anonymousYn: post.value.anonymous === true ? true : false,
                    content: post.value.content
                }

                const response = await postApi.savePostById(roomId, data);
                console.log(response);

                alert("저장되었습니다.");

                window.location.href = `/room/${roomId}`;
            } catch (error) {
                console.error("Error saving post:", error);
            }
        };

        const setCategory = (category) => {
            post.value.category = category;
        };

        const setAnonymous = (anonymous) => {
            post.value.anonymous = anonymous;
        };

        return {
            post,
            nickname,
            getNickname,
            submitPost,
            setCategory,
            setAnonymous
        }
    }

    // name: 'SubmitPost',

    // data() {
    //     return {
    //         title: '',
    //         anonymous: false,
    //         content: '',
    //         category: 'Free'
    //     };
    // },

    // methods: {
    //     submitPost() {
    //         console.log('제목:', this.title);
    //         console.log('작성자:', this.post.writer);
    //         console.log('익명 여부:', this.anonymous);
    //         console.log('내용:', this.content);
    //         console.log('카테고리:', this.category);
    //     },

    //     setCategory(category) {
    //         this.category = category;
    //     },

    //     setAnonymous(anonymous) {
    //         this.anonymous = anonymous;
    //     }
    // }
}
</script>

<style scoped>
body {
    margin: 0;
}

.post-text {
    font-size: 24px;
    color: white;
    font-weight: bold;
}

.submit-post {
    background-color: #5F73B0;
    padding: 20px;
    border-radius: 10px;
    flex-direction: column;
    margin: 0;
    margin-bottom: 50px;
    padding: 0 400px;
}

.form-row {
    display: flex;
    justify-content: space-between;
    margin-bottom: 20px;
}

.form-group {
    flex: 0 0 48%;
}

.form-group label {
    flex: 1;
}

.form-group select {
    width: 80%;
    padding: 20px;
    border-radius: 15px;
    border: 1px solid #ccc;
}

.form-group input[type="text"],
.form-group textarea,
.form-group select {
    flex: 2;
    margin-left: 10px;
}

label {
    display: block;
    margin-bottom: 5px;
    color: white;
}

input[type="text"],
textarea,
select {
    width: 100%;
    padding: 20px;
    border-radius: 15px;
    border: 1px solid #ccc;
}

.submit-post textarea#content {
    width: calc(100% - 70px);
    height: 200px;
}

.category-button-group {
    display: flex;
}

.category-button-group button {
    flex: 1;
    padding: 10px;
    margin-right: 10px;
    border: 2px solid transparent;
    border-radius: 5px;
    cursor: pointer;
    outline: none;
}

.category-button-group button:nth-child(1) {
    background-color: #D9D9D9;
    color: black;
}

.category-button-group button:nth-child(2) {
    background-color: #97B4FF;
    color: black;
}

.category-button-group button:nth-child(3) {
    background-color: #FFBBBB;
    color: black;
}

.category-button-group button.active {
    border-color: #FF9900;
    border-width: 4px;
}

.anonymous-button-group {
    display: flex;
}

.anonymous-button-group button {
    flex: 1;
    padding: 10px;
    margin-right: 10px;
    border: 2px solid transparent;
    border-radius: 5px;
    cursor: pointer;
    outline: none;
}

.anonymous-button-group button:nth-child(1) {
    background-color: #97B4FF;
    color: black;
}

.anonymous-button-group button:nth-child(2) {
    background-color: #FFBBBB;
    color: black;
}

.anonymous-button-group button.active {
    border-color: #FF9900;
    border-width: 4px;
}

</style>