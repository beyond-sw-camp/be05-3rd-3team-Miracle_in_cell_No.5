<template>
    <!-- <div class="post-card">
        <p>제목: {{ post.title }}</p>
        <span>작성자: {{ post.authorNickname }}</span>
        <span>카테고리: {{ post.category }}</span>
        <span>조회수: {{ post.viewCount }}</span>
        <span>작성일: {{ post.createdDateTime }}</span>
    </div> -->
    <div class="post-card">
        <router-link :to="`/posts/${post.id}`">
            <p>제목: {{ post.title }}</p>
        </router-link>
        <div style="display: flex; justify-content: space-between;">
            <span style="margin-right: 2ch;">작성자: {{ post.authorNickname }}</span>
            <span style="margin-right: 2ch;">카테고리: {{ post.category }}</span>
            <span style="margin-right: 2ch;">조회수: {{ post.viewCount }}</span>
            <span>작성일: {{ post.createdDateTime }}</span>
        </div>
    </div>
    
</template>

<script>
export default {
    props: {
        post: {
            type: Object,
            required: true
        }
    }
}
</script>

<style>
.post-card {
    border: 1px solid #ccc;
    border-radius: 5px;
    padding: 10px;
    margin-bottom: 10px;
}
</style>