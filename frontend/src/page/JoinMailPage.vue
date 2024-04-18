<template>
  <main class="form-signin text-center">
    <form>
      <img class="mb-4 rounded" src="../img/logo-orange-login.png" width="300" height="150">
      <h1 class="h3 mb-3 fw-normal">메일 인증</h1>
      <div class="mb-3 form-floating">
        <input class="form-control mb-3" type="email" id="email" name="email"
               placeholder="이메일" required
               v-bind:readonly="isReadOnly"
               v-model="email">
        <label for="email" class="text-secondary">이메일</label>
        <div class="text-join-info">한화 SW Camp 5기 수강생만 가입이 가능합니다!</div>
      </div>

      <!-- 이메일 인증 번호 입력란 -->
      <div v-if="isMailSend" class="mb-3 form-floating">
        <input class="form-control mb-3" type="text" id="authNum" name="authNum"
               placeholder="이메일" required
               v-model="authNum">
        <label for="email" class="text-secondary">이메일 인증 번호</label>
      </div>

      <div>
        <button class="w-100 btn btn-lg btn-primary" type="button" @click="mailSend" v-if="!isMailSend">
          <span id="emailVerificationBtn">이메일 인증 번호 발송</span>
          <span class="spinner-border spinner-border-sm d-none" id="loadingSpinner" role="status"
                aria-hidden="true"></span>
        </button>
      </div>

      <div>
        <button class="w-100 btn btn-lg btn-primary" type="button" @click="mailCheck" v-if="isMailSend">
          <span id="emailVerificationBtn">이메일 인증</span>
          <span class="spinner-border spinner-border-sm d-none" id="loadingSpinner" role="status"
                aria-hidden="true"></span>
        </button>
      </div>
    </form>
    <hr>
    <p class="text-center text-muted mt-2 mb-0">이미 계정이 있으신가요?
      <a href="/login" class="fw-bold text-body"><u>로그인</u></a></p>
  </main>
</template>

<script>
import joinApi from '@/apis/join';
import {ref} from "vue";
import {useRouter} from "vue-router";

export default {
  setup() {
    let isMailSend = ref(false);
    let email = ref('');
    let authNum = ref('');
    let isReadOnly = ref(false);

    const router = useRouter();

    let mailSend = async () => {
      const response = await joinApi.postSendMail({
        email: email.value
      });
      if (response.data.isSuccess) {
        isMailSend.value = true;
        isReadOnly.value = true;
      } else {
        alert(response.data.message);
      }
    }

    let mailCheck = async () => {
      const response = await joinApi.postMailCheck({
        email: email.value,
        authNum: authNum.value
      });
      if (response.data.isSuccess) {
        alert('인증 성공');
        sessionStorage.setItem('email', email.value);
        router.push('/join/info');
      } else {
        alert(response.data.message);
      }
    }

    return {
      isMailSend,
      mailSend,
      email,
      authNum,
      mailCheck,
      isReadOnly
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

.text-join-info {
  color: #a0a0a0;
  font-size: 0.8rem;
}

</style>