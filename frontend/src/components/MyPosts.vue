<template>
  <div class="container my-3 custom-background"> <!-- 사용자 정의 배경색을 컨테이너에 적용 -->
    <div class="row">
      <div class="col-md-4 mb-3" v-for="post in posts" :key="post.id">
        <div class="card custom-card-color"> <!-- 사용자 정의 카드 색상 적용 -->
          <div class="card-body">
            <h5 class="card-title">{{ post.title }}</h5>
            <h6 class="card-subtitle mb-2">방: {{ post.room }}</h6>
            <p class="card-text">카테고리: {{ post.category }}</p>
            <p class="card-text">작성일: {{ new Date(post.createdAt).toLocaleDateString() }}</p>
            <a href="#" class="btn btn-outline-secondary btn-sm" @click="viewPost(post.id)">보기</a>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import User from '@/apis/user';

export default {
  name: 'MyPosts',
  data() {
    return {
      posts: []
    };
  },
  methods: {
    fetchPosts() {
      User.getUserPosts()
        .then(response => {
          this.posts = response.data;
        })
        .catch(error => {
          console.error(error);
        });
    },
    viewPost(postId) {
      this.$router.push({ name: 'PostDetail', params: { id: postId } });
    }
  },
  created() {
    this.fetchPosts();
  }
};
</script>

<style scoped>
/* 사용자 정의 색상 변수 선언 */
:root {
  --card-bg-color: #f0e68c; /* 카드 배경색 (카키색) */
  --background-color: #f5f5dc; /* 배경색 (베이지색) */
}

.custom-card-color {
  background-color: rgb(221, 204, 167); /* 변수로 지정된 색상 사용 */
  color: black; /* 카드 내용의 텍스트 색상 */
}

.custom-background {
  background-color: #5F73B0; /* 변수로 지정된 색상 사용 */
  height: 100vh;
  /* 추가적인 배경 스타일링을 여기에 작성하세요 */
}

.container {
  padding: 20px; /* 컨테이너 패딩 조정 */
}

/* 카드 간격과 반응형 디자인을 위한 추가 스타일링 */
.card {
  margin-bottom: 1rem;
}

@media (max-width: 768px) {
  .card {
    width: 100%; /* 모바일 뷰에서 카드 너비를 조정 */
  }
}
</style>