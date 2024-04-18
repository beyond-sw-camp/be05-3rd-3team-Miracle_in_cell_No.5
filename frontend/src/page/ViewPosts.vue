<template>
    <div id="main-container">
        <form class="search-bar" @submit.prevent="onSubmit" v-if="existPage()">
            <select v-model="selectedCategory">
                <option value="FREE">Free</option>
                <option value="COMPLEMENT">Compliment</option>
                <option value="CLAIMS">Complain</option>
            </select>
            <input type="text" v-model="searchQuery" placeholder="검색어를 입력하세요.">
            <button type="submit">검색</button>
        </form>

        <div class="posts-container">
            <div v-for="(post) in posts" :key="post.id">
                <PostCard :post="post"/>
            </div>
        </div>

       <!-- 페이지네이션 -->
        <nav aria-label="Page navigation example" v-if="existPage()">
            <ul class="pagination justify-content-center">
                <li class="page-item" >
                <div class="btn page-link" aria-label="Previous" @click="previousPage">
                    <span aria-hidden="true">&laquo;</span>
                </div>
                </li>

                <li class="page-item" v-for="pageNumber in totalPages" :key="pageNumber">
                    <button class="page-link" @click="goToPage(pageNumber)">{{ pageNumber }}</button>
                </li>

                <li class="page-item" >
                <div class="btn page-link" aria-label="Next" @click="nextPage">
                    <span aria-hidden="true">&raquo;</span>
                </div>
                </li>
            </ul>
        </nav>
        <!-- 페이지 없는 경우 -->
        <div v-if="!existPage()"> 
            글이 존재하지 않습니다.
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
        const posts = ref([]);
        const route = useRoute();
        const roomId = route.params.id;
        // search
        const searchQuery = ref('');
        const selectedCategory = ref('');
        //pagenaion
        const first = ref(); 
        const last = ref(); 
        const page = ref(1) ; // 초기 페이지
        const totalPages = ref(1);
        // const totalElements = ref() ;


        const getPost = async () => {
            try {
                console.log(roomId);
                const response = await searchApi.getSearchPosts(roomId, searchQuery.value,selectedCategory.value, page.value);
                console.log("getPost data data , " , response.data.data);
                posts.value = response.data.data.content;
                totalPages.value = response.data.data.totalPages ;
                first.value = response.data.data.first ;
                last.value = response.data.data.last ;
                page.value = response.data.data.pageable.pageNumber+1 ;
                // totalElements.value = response.data.data.totalElements ;
            } catch (error) {
                console.error("Error fetching scraps:", error);
            }
        }
        getPost();

        const goToPage = (pageNumber) => {
            if (pageNumber > 0 && pageNumber <= totalPages.value) {
                page.value = pageNumber;
                console.log("goToPage pageNumber : " ,page.value);
                getPost();
            }
        };

        // const previousPage = () => {
        //     if (!first.value) {
        //         console.log("previousPage pageNumber",--page.value+1) ;
        //         getPost();
        //     }
        // };

        // const nextPage = () => {
        //     if (!last.value) {
        //         console.log("nextPage pageNumber", ++page.value+1);
        //         getPost();
        //     }
        // };
        const previousPage = () => {
            if (page.value > 1) {
                page.value--;
                console.log("previousPage pageNumber", page.value);
                getPost();
            }
        };

        const nextPage = () => {
            if (page.value < totalPages.value) { // totalPages는 전역으로 설정되어 있어야 합니다.
                page.value++;
                console.log("nextPage pageNumber", page.value);
                getPost();
            }
        };

        const onSubmit = () => {
            page.value = 1; // 검색할 때 페이지를 1로 리셋
            getPost();
        };
        
        const existPage = () => {
            // console.log(typeof(totalPages.value))
            return totalPages.value > 0 ;
        }

        return {
            // postList
            posts,
            onSubmit,
            searchQuery,
            selectedCategory,
            roomId,
            page,
            goToPage,
            previousPage,
            nextPage ,
            totalPages,
            existPage
        }
    }

}
</script>

<style scoped>
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