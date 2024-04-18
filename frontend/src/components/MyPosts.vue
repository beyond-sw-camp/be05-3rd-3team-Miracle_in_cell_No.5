
<template>
  <div class="user-posts">
    <div class="container py-4">
      <div class="row row-cols-1 row-cols-md-4 g-4">
        <div class="col" v-for="post in posts" :key="post.id">
          <div class="card h-100">
            <div class="card-body d-flex flex-column">
              <h5 class="card-title">{{ post.title }}</h5>
              <p class="card-text">ROOM : {{ post.roomUserNickname }}</p>
              <p class="card-text">{{ post.category }}</p>
              <p class="card-text"><small>{{ post.createdDateTime }}</small></p>
              <div class="mt-auto">
                <button class="btn btn-primary w-100" @click="viewPost(post.id)">View</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import userApi from '@/apis/user';
import { useRouter } from 'vue-router'; // vue-router에서 useRouter를 가져옵니다.

export default {
  setup() {
    const posts = ref([]);
    const router = useRouter(); // 라우터 인스턴스를 사용합니다.

    const fetchPosts = async () => {
      try {
        const response = await userApi.getUserPosts();
        posts.value = response.data.data.content;
        console.log(response.data.data.content);
      } catch (error) {
        console.error('정보를 불러오지 못했습니다:', error);
      }
    };

    const viewPost = (id) => {
      router.push(`/posts/${id}`); // 여기에서 URL로 이동합니다.
      console.log('자세히보기:', id);
    };

    onMounted(fetchPosts);

    return {
      posts,
      viewPost
    };
  }
}
</script>

<style scoped>
.user-posts {
  background-color: #5F73B0; /* 배경색 설정 */
  padding: 1rem 0; /* 상하 패딩을 줘서 카드와의 간격을 조정합니다. */
  height: 100vh;
}

.user-posts .container {
  max-width: 1240px; /* or your preferred max width */
}

.card {
  box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2); /* Adds shadow to the cards */
  transition: 0.3s; /* Smooth transition for hover effect */
}

.card:hover {
  box-shadow: 0 8px 16px 0 rgba(0,0,0,0.2); /* Slightly larger shadow on hover */
}

.card-body {
  display: flex;
  flex-direction: column;
}

.btn-primary {
  margin-top: auto; /* Pushes the button to the bottom of the card */
}


</style>