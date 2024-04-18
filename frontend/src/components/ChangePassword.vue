<template>
  <div class="password-change-container">
    <form @submit.prevent="checkPasswordMatch" class="password-form">
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
import {ref} from 'vue';
import {useRouter} from 'vue-router';
import userApi from '@/apis/user';

export default {
  name: 'PasswordChange',
  setup() {
    const router = useRouter();
    const oldPassword = ref('');
    const newPassword = ref('');
    const confirmPassword = ref('');
    const passwordMismatch = ref(false);

    const checkPasswordMatch = () => {
      passwordMismatch.value = newPassword.value !== confirmPassword.value;
      if (!passwordMismatch.value) {
        changePassword();
      }
    };

    const changePassword = async () => {
      const data = {
        currentPassword: oldPassword.value,
        resetPassword: newPassword.value,
        confirmResetPassword: confirmPassword.value
      };
      const response = await userApi.patchPassword(data);
      if (response.data.isSuccess) {
        alert('비밀번호가 변경되었습니다.');
        router.push('/');
      } else {
        alert(response.data.message);
      }
    };

    return {
      oldPassword,
      newPassword,
      confirmPassword,
      passwordMismatch,
      checkPasswordMatch
    };
  }
};
</script>

<style scoped>
.password-change-container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: #5F73B0;
  padding: 200px;
  padding-top: 1px;
  min-height: 100vh;
  color: black;
}

.password-form {
  width: 100%;
  max-width: 600px;
  background-color: white;
  padding: 40px;
  border-radius: 15px;
  color: black;
}

.form-group {
  margin-bottom: 1.5rem;
  color: black;
}

.form-control {
  font-size: 1.25rem;
  padding: 15px 24px;
  height: auto;
  margin-bottom: 20px;
}

.btn-primary {
  width: 100%;
  padding: 12px;
  font-size: 1.25rem;
}

.text-danger {
  font-size: 1rem;
  margin-top: 5px;
}
</style>
