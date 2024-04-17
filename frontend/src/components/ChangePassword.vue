<template>
  <div class="password-change-container">
    <form @submit.prevent="checkPasswordMatch" class="password-form">
      <h3 class="text-center my-4">비밀번호 변경</h3>
      <div class="form-group">
        <label for="oldPassword">기존 비밀번호</label>
        <input type="password" id="oldPassword" v-model="oldPassword" class="form-control" required>
      </div>
      <div class="form-group">
        <label for="newPassword">새로운 비밀번호</label>
        <input type="password" id="newPassword" v-model="newPassword" class="form-control" required>
      </div>
      <div class="form-group">
        <label for="confirmPassword">비밀번호 확인</label>
        <input type="password" id="confirmPassword" v-model="confirmPassword" class="form-control" required>
        <p v-if="passwordMismatch" class="text-danger">비밀번호가 일치하지 않습니다.</p>
      </div>
      <button type="submit" class="btn btn-primary" :disabled="passwordMismatch">확인</button>
    </form>
  </div>
</template>

<script>
import userApi from '@/apis/user'; 

export default {
  name: 'PasswordChange',
  data() {
    return {
      oldPassword: '',
      newPassword: '',
      confirmPassword: '',
      passwordMismatch: false
    };
  },
  methods: {
    checkPasswordMatch() {
      this.passwordMismatch = this.newPassword !== this.confirmPassword;
      if (!this.passwordMismatch) {
        this.changePassword();
      }
    },
    changePassword() {
      const data = {
        oldPassword: this.oldPassword,
        newPassword: this.newPassword
      };
      userApi.patchPassword(data)
        .then(() => {
          alert('비밀번호가 성공적으로 변경되었습니다.');
          // 성공 후 추가적인 액션 (예: 페이지 리다이렉트)
        })
        .catch(error => {
          console.error('비밀번호 변경 실패:', error);
          alert('비밀번호 변경에 실패했습니다. 다시 시도해 주세요.');
        });
    }
  }
};
</script>

<style scoped>
.password-change-container {
  display: flex;
  flex-direction: column;
  justify-content: flex-start; /* 가운데 정렬에서 시작 정렬로 변경 */
  align-items: center;
  background-color: #5F73B0;
  padding: 20px;
  padding-top: -10vh; /* 상단에 더 적은 패딩을 적용하여 위로 이동 */
  min-height: 100vh;
}

.password-form {
  width: 100%;
  max-width: 600px;
  background-color: white;
  padding: 40px;
  border-radius: 15px;
  transform: translateY(40%); /* Y축 방향으로 약간 위로 이동 */
}

.form-group {
  margin-bottom: 1.5rem;
}

.form-control {
  font-size: 1.25rem; /* 입력 필드의 글꼴 크기 증가 */
  padding: 15px 24px; /* 입력 필드의 패딩 증가 */
  height: auto; /* 입력 필드의 높이를 자동으로 조정 */
  margin-bottom: 20px; /* 여백 추가 */
}

.btn-primary {
  width: 100%; /* 버튼의 너비를 폼과 동일하게 설정 */
  padding: 12px; /* 버튼 패딩을 늘림 */
  font-size: 1.25rem; /* 버튼의 글꼴 크기 증가 */
}

.text-danger {
  font-size: 1rem;
  margin-top: 5px;
}

/* 추가 스타일링이 필요한 경우 여기에 작성하세요 */
</style>

