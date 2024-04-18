<template>
  <main class="form-signin text-center">
    <form @submit.prevent="login">
      <img class="mb-4 rounded" src="../img/logo-orange-login.png" width="300" height="150">

      <div class="mb-3 form-floating">
        <input class="form-control mb-3" type="text" id="username" name="username"
               placeholder="아이디" required
               v-model="username">
        <label for="username" class="text-secondary">아이디</label>
      </div>
      <div class="mb-3 form-floating">
        <input class="form-control mb-1" type="password" id="password" name="password"
               placeholder="비밀번호" required
               v-model="password">
        <label for="password" class="text-secondary">비밀번호</label>
      </div>
      <button class="w-100 btn btn-lg btn-primary" type="submit">로그인</button>
      <hr>
      <p class="text-center text-muted mt-2 mb-0">계정이 없으신가요?
        <a href="/join/mail" class="fw-bold text-body"><u>회원가입</u></a></p>

      <p class="text-center text-muted mt-2 mb-0">비밀번호를 잊어버리셨나요?
        <a href="/password/step1" class="fw-bold text-body"><u>비밀번호 찾기</u></a>
      </p>
    </form>
  </main>
</template>

<script>
import loginApi from '@/apis/login';
import {ref} from "vue";
import {useRouter} from "vue-router";

export default {
  setup() {
    const username = ref();
    const password = ref();

    const router = useRouter();

    const login = async () => {
      try {
        const formData = new FormData();
        formData.append('username', username.value);
        formData.append('password', password.value);
        const response = await loginApi.postLogin(formData);
        localStorage.setItem('token', response.headers.authorization);
        if (response.data.isSuccess) {
          router.push('/');
        } else {
          alert(response.data.message);
        }
      } catch (e) {
        console.log("edit room putRoom error, ", e)
      }
    }

    return {
      username,
      password,
      login
    }
  }
}
</script>

<style scoped>
.field-error {
  border-color: #dc3545;
  color: #dc3545;
}

html,
body {
  height: 100%;
}

body {
  display: flex;
  align-items: center;
  padding-top: 40px;
  padding-bottom: 40px;
  background-color: #f5f5f5;
}

.form-signin {
  width: 100%;
  max-width: 330px;
  padding: 15px;
  margin: auto;
}

.form-signin .checkbox {
  font-weight: 400;
}

.form-signin .form-floating:focus-within {
  z-index: 2;
}

.form-signin input[type="email"] {
  margin-bottom: -1px;
  border-bottom-right-radius: 0;
  border-bottom-left-radius: 0;
}

.form-signin input[type="password"] {
  margin-bottom: 10px;
  border-top-left-radius: 0;
  border-top-right-radius: 0;
}

</style>