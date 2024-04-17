<template>
    <div class="whole-container">
      <!-- MyPageHeader 컴포넌트를 포함합니다 -->
      <MyPageHeader />
  
      <!-- 게시글 목록을 나열하는 섹션 -->
      <div class="admin-posts-container">
        <table class="table">
          <thead>
            <tr>
              <th scope="col">제목</th>
              <th scope="col">작성자</th>
              <th scope="col">작성한 방</th>
              <th scope="col">작성 일자</th>
              <th scope="col">삭제</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(post, index) in posts" :key="index">
              <td>{{ post.title }}</td>
              <td>{{ post.author }}</td>
              <td>{{ post.room }}</td>
              <td>{{ post.date }}</td>
              <td>
                <button @click="deletePost(post.id)" class="btn btn-danger">X</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </template>
  
  <script>
  import MyPageHeader from '@/components/MyPageHeader.vue';
  
  export default {
    components: {
      MyPageHeader
    },
    data() {
      return {
        // 더미 데이터입니다. 실제 백엔드 연결 시 대체됩니다.
        posts: [
          { id: 1, title: '제목 1', author: '작성자 1', room: '방 1', date: '2024-01-01' },
          { id: 1, title: '제목 1', author: '작성자 1', room: '방 1', date: '2024-01-01' },
          { id: 1, title: '제목 1', author: '작성자 1', room: '방 1', date: '2024-01-01' },
          { id: 1, title: '제목 1', author: '작성자 1', room: '방 1', date: '2024-01-01' }
        ]
      };
    },
    methods: {
      deletePost(postId) {
        // 여기에서 삭제 로직을 구현합니다.
        // 이 예시에서는 더미 데이터를 필터링하여 삭제합니다.
        this.posts = this.posts.filter(post => post.id !== postId);
      }
    }
  };
  </script>
  
  <style scoped>
  .admin-posts-container {
    max-width: 1200px;
    margin: 2rem auto;
    padding: 1rem;
    background: #fff;
    border-radius: 0.5rem;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  }

  .table {
    margin-top: 1rem;
  }

  thead th {
    background-color: #f8f9fa;
  }

  tbody tr {
    transition: background-color 0.3s ease;
  }

  tbody tr:hover {
    background-color: #f1f3f5;
  }

  .btn-danger {
    background-color: #dc3545;
    border: none;
  }

  .btn-danger:hover {
    background-color: #c82333;
  }

  /* 반응형 디자인을 위한 추가 스타일 */
  @media (max-width: 768px) {
    .admin-posts-container {
      padding: 0.5rem;
    }
    .table {
      font-size: 0.9rem;
    }
  }

  .whole-container{
    background-color: gray;
    height: 100vh;
  }
</style>