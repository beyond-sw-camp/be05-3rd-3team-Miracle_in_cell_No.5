<template>
    <div class="sidebar d-flex flex-column justify-content-center">
      <router-link to="/mypage/my-info" class="sidebar-item">내 정보 관리</router-link>
      <router-link to="/mypage/my-posts" class="sidebar-item">내가 쓴 글</router-link>
      <router-link to="/mypage/change-password" class="sidebar-item mb-4">비밀번호 수정</router-link>
      <button class="logout-button mt-3">로그아웃</button>
    </div>
  </template>
  
  <script>
  export default {
    
  };
  </script>
  
  <style scoped>
  .sidebar {
    /* 사이드바 스타일링 */
    background-color: #dedfe0; /* 연한 회색 배경 */
    width: 380px; /* 넓이 설정 */
    height: auto;

  }
  
  .sidebar-item {
    color: #cf923e; /* 글자를 검은색으로 */
    font-size: 1.3rem; /* 글자 크기 키우기 */
    padding: 0.5rem 1rem; /* 패딩으로 위아래 공간 추가 */
    text-align: center; /* 글자 가운데 정렬 */
    text-decoration: none; /* 밑줄 없애기 */
    font-weight: 550;
    display: block; /* 블록 요소로 만들어 온전히 클릭 가능한 영역 확보 */
  }

  .sidebar-item.mb-4 {
  margin-bottom: 1.5rem; /* 버튼과의 간격을 조정 */
}
  
  .sidebar-item:not(:last-child) {
    margin-bottom: 0.5rem; /* 아이템 사이의 간격 추가 */
  }
  
  /* 부트스트랩이 적용되지 않는 경우 아래 코드를 사용 */
  .router-link-active {
    color: #495057; /* 활성화된 링크 색상 변경 */
    background-color: #b5c6d7; /* 활성화된 링크 배경색 변경 */
    border-radius: 0.25rem; /* 둥근 테두리 추가 */
  }

  .logout-button {
  padding: 10px 20px;
  background-color: rgb(228, 182, 96); /* 버튼 배경색 */
  color: black; /* 버튼 글자색 */
  border: none; /* 테두리 제거 */
  border-radius: 20px; /* 둥근 모서리 */
  cursor: pointer; /* 마우스 오버 시 커서 변경 */
  margin: 1rem auto;
  

}
/* 부트스트랩의 .btn 클래스 스타일이 적용되지 않는 경우 */
.logout-button:hover {
  background-color: #e0e0e0; /* 호버 시 배경색 변경 */
}
  </style>