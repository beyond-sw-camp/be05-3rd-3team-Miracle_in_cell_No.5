<template>
    <div class="whole-container">
      <MyPageHeader />
  
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
            <tr v-for="post in posts" :key="post.id">
              <td>{{ post.title }}</td>
              <td>{{ post.authorNickname }}</td>
              <td>{{ post.roomUserNickname }}</td>
              <td>{{ post.createdDateTime }}</td>
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
  import { ref, onMounted } from 'vue';
  import MyPageHeader from '@/components/MyPageHeader.vue';
  import adminApi from '@/apis/admin';
  
  export default {
    components: {
      MyPageHeader
    },
    setup() {
      const posts = ref([]);
  

      const loadPosts = async () => {
        try {
          const response = await adminApi.getViewAllPosts();
      
          console.log(response.data.data.content);
    
          posts.value = response.data.data.content; 
        } catch (error) {
          console.error('Error fetching posts:', error);
        }
      };
  
      const deletePost = async (postId) => {
        try {
          await adminApi.deletePost(postId);
          loadPosts(); 
        } catch (error) {
          console.error('Error deleting post:', error);
        }
      };
  
  
      onMounted(loadPosts);
  

      return {
        posts,
        deletePost
      };
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