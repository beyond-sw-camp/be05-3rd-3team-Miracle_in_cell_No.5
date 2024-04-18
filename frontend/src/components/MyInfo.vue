<template>
  <div class="my-info-container">
    <div class="container">
      <div class="row justify-content-center">
        <div class="col-md-8">
          <div class="profile-card mb-3">
            <div class="profile-image">
              <img :src="`http://localhost:8080/images/${user.profileImage}`" alt="Profile Image" class="profile-img"/>
            </div>
            <h3 class="user-name">{{ user.nickname }}</h3>
          </div>
          <div class="user-details">
            <div class="mb-3">
              <label for="userId" class="form-label">아이디</label>
              <input type="text" class="form-control-plaintext" readonly :value="user.loginId"/>
            </div>
            <div class="mb-3">
              <label for="userEmail" class="form-label">이메일</label>
              <input type="email" class="form-control-plaintext" readonly :value="user.gitEmail"/>
            </div>
            <div class="mb-3">
              <label for="joinDate" class="form-label">가입 일자</label>
              <input type="text" class="form-control-plaintext" readonly :value="formatDate(user.createdDateTime)"/>
            </div>
            <div class="button-row">
              <button class="edit-btn" @click="openModal('profileImage')">프로필 사진 수정</button>
              <button class="edit-btn" @click="openModal('nickname')">닉네임 수정</button>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 모달 컴포넌트 -->
    <div v-if="showModal" class="modal">
      <div class="modal-content">
        <span class="close" @click="closeModal">&times;</span>
        <h3>{{ modalTitle }}</h3>
        <input v-if="editing === 'profileImage'" type="file" @change="handleFileUpload"/>
        <input v-else v-model="tempData" type="text"/>
        <button @click="updateData">저장</button>
      </div>
    </div>
  </div>
</template>

<script>
import {onMounted, ref} from 'vue';
import userApi from '@/apis/user';

export default {
  setup() {
    const user = ref({
      profileImage: '',
      nickname: '',
      loginId: '',
      gitEmail: '',
      createdDateTime: ''
    });
    const showModal = ref(false);
    const editing = ref('');
    const tempData = ref('');
    const modalTitle = ref('');

    const openModal = (mode) => {
      editing.value = mode;
      modalTitle.value = mode === 'profileImage' ? '프로필 사진 수정' : '닉네임 수정';
      showModal.value = true;
      tempData.value = mode === 'nickname' ? user.value.nickname : '';
    };

    const closeModal = () => {
      showModal.value = false;
    };

    const updateData = async () => {
      if (editing.value === 'nickname') {
        const data = {nickname: tempData.value};
        try {
          const response = await userApi.patchProfile(data);
          user.value.nickname = tempData.value;
          console.log(response);
          alert('닉네임이 성공적으로 변경되었습니다.');
        } catch (error) {
          console.error('Error updating nickname:', error.response);
          const errorMessage = error.response && error.response.data && error.response.data.message
              ? error.response.data.message
              : '닉네임 변경에 실패했습니다.';
          alert(errorMessage);
        }
      } else if (editing.value === 'profileImage') {
        const imageData = tempData.value.split(',')[1]; // Base64 인코딩된 데이터
        const data = {profileImage: imageData}; // DTO의 필드명에 맞춰서 객체 구성
        try {
          await userApi.patchProfileImage(data);
          user.value.profileImage = tempData.value; // 화면에 표시할 이미지 경로를 업데이트
          alert('프로필 사진이 성공적으로 변경되었습니다.');
        } catch (error) {
          console.error('Error updating profile image:', error.response);
          alert('프로필 사진 변경에 실패했습니다.');
        }
      }
      closeModal();
    };

    const handleFileUpload = event => {
      const files = event.target.files;
      if (files.length > 0) {
        const file = files[0];
        const reader = new FileReader();
        reader.onload = e => {
          tempData.value = e.target.result; // Base64 데이터
        };
        reader.readAsDataURL(file);
      }
    };

    const fetchUserProfile = async () => {
      const response = await userApi.getLoginUserProfile();
      if (response.data.data) {
        user.value = response.data.data;
      }
    };

    const formatDate = (dateString) => {
      const options = {year: 'numeric', month: 'long', day: 'numeric'};
      return new Date(dateString).toLocaleDateString(undefined, options);
    };

    onMounted(() => {
      fetchUserProfile();
    });

    return {
      user,
      showModal,
      editing,
      tempData,
      openModal,
      closeModal,
      updateData,
      handleFileUpload,
      modalTitle,
      formatDate
    };
  }
}
</script>


<style scoped>

.my-info-container {
  display: flex;
  flex-direction: column;
  align-items: center; /* 가로 방향 가운데 정렬 */
  justify-content: flex-start; /* 세로 방향 시작 부분 정렬 */
  height: 100%;
  background-color: #5F73B0;
  padding: 20px;
}

.profile-card {
  text-align: center;
  background-color: #B6D39D;
  border-radius: 16px;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
  padding: 20px;
  margin: 10px auto; /* 상하 여백을 줄이고 좌우는 자동으로 설정 */
  width: 80%; /* 이 값을 다른 박스의 너비와 일치시킵니다. */
  max-width: 300px; /* 최대 너비 조정 */
}

.profile-image {
  position: relative;
  display: flex; /* Flexbox를 사용 */
  justify-content: center; /* 가로 방향으로 가운데 정렬 */
  width: 100%; /* 전체 너비 */
  margin-bottom: 20px; /* 아래쪽 여백 */
}

.user-data {
  width: 100%; /* 데이터 영역 너비 */
  margin-bottom: 10px; /* 데이터 영역 간 여백 */
}

.user-data label {
  font-weight: bold; /* 라벨 굵게 */
}

.user-data p {
  background-color: #e9ecef; /* 입력란 배경 색상 */
  border-radius: 20px; /* 둥근 모서리 */
  padding: 10px 20px; /* 패딩 */
  margin: 0; /* 기본 마진 제거 */
}

.button-row {
  display: flex;
  justify-content: space-evenly; /* 버튼들 간의 공간을 균등하게 분배 */
  margin-top: 20px; /* 가입일자 정보 밑에 여백 추가 */
}

.edit-btn {
  background: #ffa500; /* 버튼 색상 */
  border: none;
  border-radius: 20px;
  padding: 10px 20px;
  cursor: pointer;
}


.edit-btn:hover {
  background: #cc8400; /* 호버 시 버튼 색상 변경 */
}

.form-label {
  color: white;
  font-size: 20px;

}

.form-control-plaintext {
  background-color: #ebf5e3;
  border-radius: 16px;
  padding: 8px;
  font-size: 25px;
}

.user-name {
  font-size: 25px;
  text-align: center;
}

.profile-image img {
  width: 100%; /* 이미지를 부모의 너비에 맞춥니다. */
  height: 100%; /* 이미지를 부모의 높이에 맞춥니다. */
  object-fit: cover; /* 이미지가 요소를 채우면서 비율이 유지되도록 합니다. */
  border-radius: 50%; /* 이미지를 원형으로 만듭니다. */
}

.user-details {
  width: 80%; /* .profile-card와 동일한 너비를 설정 */
  text-align: center;
  margin: 0 auto; /* 좌우 여백을 자동으로 설정하여 가운데 정렬 */
}

.container {
  max-width: 540px; /* 부트스트랩 md 사이즈에 맞춰 컨테이너 최대 너비 조정 */
}

.row {
  margin: 0; /* 부트스트랩 row의 기본 마진 제거 */
}

.col-md-8 {
  flex: 0 0 100%; /* 부트스트랩 col-md-8을 전체 너비로 조정 */
  max-width: 100%; /* 최대 너비를 100%로 조정 */
}

.modal {
  position: fixed;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.75);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-content {
  background: #fff;
  padding: 30px;
  border-radius: 12px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  width: 90%;
  max-width: 400px;
  text-align: center;
  position: relative;
}

.close {
  position: absolute;
  top: 10px;
  right: 15px;
  font-size: 25px;
  font-weight: bold;
  color: #aaa;
  cursor: pointer;
}

.close:hover {
  color: #333;
}

/* 입력 필드 스타일 업데이트 */
.input {
  width: 100%; /* 전체 너비를 사용하도록 설정 */
  padding: 12px 20px;
  margin-top: 20px;
  border-radius: 8px;
  border: 1px solid #ccc;
  box-shadow: inset 0 1px 3px rgba(0, 0, 0, 0.1); /* 내부 그림자 추가 */
  font-size: 16px;
  transition: border-color 0.3s, box-shadow 0.3s; /* 부드러운 전환 효과 추가 */
}

.input:focus {
  border-color: #007bff; /* 포커스 상태일 때 테두리 색상 변경 */
  box-shadow: inset 0 1px 3px rgba(0, 0, 0, 0.2), 0 0 8px rgba(0, 123, 255, 0.6); /* 포커스 시 그림자 강조 */
}

/* 저장 버튼 스타일 업데이트 */
.edit-btn {
  width: 100%; /* 버튼의 너비를 조정 */
  padding: 12px 20px;
  margin-top: 30px; /* 상단 여백 조정 */
  background-color: #007bff; /* 버튼 색상 변경 */
  color: white; /* 텍스트 색상 변경 */
  border: none;
  border-radius: 8px;
  font-size: 16px;
  letter-spacing: 1px; /* 글자 간격 설정 */
  cursor: pointer;
  transition: background-color 0.3s; /* 색상 변경시 부드러운 전환 */
}
</style>