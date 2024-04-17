<template>
    <div id="main-container">
        <div class="search-bar">
            <select v-model="selectedCategory">
                <option value="Free">Free</option>
                <option value="Compliment">Compliment</option>
                <option value="Complain">Complain</option>
            </select>
            <input type="text" v-model="searchQuery" placeholder="검색어를 입력하세요.">
            <button @click="search">검색</button>
        </div>

        <div class="posts-container">
            <div v-for="(post) in posts" :key="post.id">
                <PostCard :post="post"/>
            </div>
        </div>
    </div>
</template>

<script>
import searchApi from '@/apis/room' ;
import PostCard from '@/components/PostCard.vue';
import { ref } from 'vue';
import { useRoute } from 'vue-router';

export default {
    components: {
        PostCard
    },

    setup() {
        const route = useRoute();
        const roomId = route.params.id;
        //const postList = ref([]);
        console.log("roomId: ", roomId);
        const posts = ref([]);

        const getPost = async () => {
            try {
                console.log(roomId);
                const response = await searchApi.getSearchPosts(roomId);
                console.log("debug data , " , response.data);
                console.log("debug data data , " , response.data.data);
                posts.value = response.data.data.content;
                console.log("content [0] " , posts.value[0]);
                // posts.value = postList.value.content;
            } catch (error) {
                console.error("Error fetching scraps:", error);
            }
        }
        getPost();

        return {
            // postList
            posts
        }
    }

}
</script>

<style>
.search-bar {
    display: flex;
    justify-content: flex-end;
    margin-bottom: 10px;
}

.search-bar input[type="text"], .search-bar select, .search-bar button {
    margin-left: 10px;
}

.search-bar button {
    width: 80px;
}
</style>