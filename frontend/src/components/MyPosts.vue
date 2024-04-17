<template>
  <div class="container-fluid my-posts-container mt-3">
    <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 row-cols-lg-4 g-4 justify-content-center">
      <div v-for="(post, index) in posts" :key="index" class="col">
        <div class="card h-100 text-center">
          <div class="card-header">{{ post.type }}</div>
          <div class="card-body">
            <h5 class="card-title">{{ post.title }}
              <button type="button" class="btn btn-primary btn-sm float-right">View</button>
            </h5>
            <p class="card-text">{{ post.summary }}</p>
          </div>
          <div class="card-footer text-muted">
            {{ post.date }}
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import userApi from '@/apis/user'; 

export default {
  name: 'MyPosts',
  data() {
    return {
      posts: [] // 이제 이 데이터는 백엔드로부터 가져온 게시글 데이터로 채워질 것입니다.
    };
  },
  created() {
    this.fetchUserPosts();
  },
  methods: {
    fetchUserPosts() {
      userApi.getUserPosts()
        .then(response => {
          this.posts = response.data;
        })
        .catch(error => {
          console.error('Error fetching user posts:', error);
        });
    }
  }
};
</script>
  

  <style scoped>
  .container-fluid {
    min-height:100vh;
    padding: 0 15px; /* 부트스트랩 컨테이너 패딩 제거 */
    background-color: #5F73B0; /* 전체 배경색 설정 */
  }
  
  .my-posts-container .card {
    transition: transform 0.2s; /* 마우스 호버 시 애니메이션 효과 */
    border: none; /* 카드 테두리 제거 */
    background-color: #ffffff; /* 카드 배경색 설정 */
    min-width: 400px; /* 카드의 최소 가로 길이 설정 */
  }
  
  .my-posts-container .card:hover {
    transform: scale(1.05); /* 마우스 호버 시 카드 확대 */
  }
  
  .card-header {
    background-color: #ffffff; /* 카드 헤더 배경색 설정 */
    color: #333333; /* 텍스트 색상 */
  }
  
  .card-footer {
    background-color: #ffffff; /* 카드 푸터 배경색 설정 */
    color: #333333; /* 텍스트 색상 */
  }
  
  .btn-primary {
    background-color: #007bff; /* 'View' 버튼 배경색 */
    border: none; /* 'View' 버튼 테두리 제거 */
  }
  
  .btn-primary:hover {
    background-color: #0056b3; /* 'View' 버튼 호버 색상 */
  }

  /* 반응형 */
  @media (max-width: 767px) { /* 작은 화면(모바일) */
    .my-posts-container .card {
      min-width: 100%; /* 카드가 전체 너비를 차지하도록 설정 */
      margin-bottom: 1rem; /* 카드 사이의 수직 간격 */
    }
  }

  @media (min-width: 768px) and (max-width: 991px) { /* 중간 화면(태블릿) */
    .my-posts-container .card {
      min-width: calc(50% - 20px); /* 한 줄에 2개의 카드가 들어갈 수 있도록 설정 */
      margin-bottom: 1rem; /* 카드 사이의 수직 간격 */
    }
  }

  </style>
  