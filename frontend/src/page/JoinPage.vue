<template>
  <div>
    <form class="form-signin" enctype="multipart/form-data" @submit.prevent="joinUser">
      <img class="mb-4 rounded" src="../img/logo-orange-login.png" width="300" height="150">
      <h1 class="h3 mb-3 text-sm-center">회원가입</h1>

      <div class="d-flex justify-content-center mb-4">
        <img id="selectedAvatar" src="http://localhost:8080/images/default.png"
             class="rounded-circle" style="width: 200px; height: 200px; object-fit: cover;"
             alt="프로필 사진">
      </div>
      <div class="d-flex justify-content-center">
        <div class="btn btn-primary btn-rounded">
          <label class="form-label text-white m-1" for="multipartFile">프로필 사진 선택</label>
          <input type="file" class="form-control d-none"
                 accept="image/gif, image/png, image/jpeg, image/jpg"
                 id="multipartFile"
                 @change="displaySelectedImage">
        </div>
      </div>
      <hr>

      <div class="mb-3 form-floating">
        <input class="form-control mb-3" type="email" id="email" name="email"
               placeholder="이메일" required readonly
               v-model="email">
        <label for="email" class="text-secondary">이메일</label>
      </div>

      <div class="mb-3 form-floating">
        <input class="form-control mb-3" type="text" id="loginId" name="loginId"
               placeholder="아이디" required
               v-model="loginId">
        <label for="loginId" class="text-secondary">아이디</label>
      </div>

      <div class="mb-3 form-floating">
        <input class="form-control mb-3" type="text" id="nickname" name="nickname"
               placeholder="비밀번호" required
               v-model="nickname">
        <label for="nickname" class="text-secondary">닉네임</label>
      </div>

      <div class="mb-3 form-floating">
        <input class="form-control mb-3" type="password" id="password" name="password"
               placeholder="비밀번호" required
               v-model="password">
        <label for="password" class="text-secondary">비밀번호</label>
      </div>

      <div class="mb-3 form-floating">
        <input class="form-control mb-3" type="password" id="passwordConfirm" name="passwordConfirm"
               placeholder="비밀번호" required
               v-model="passwordConfirm">
        <label for="passwordConfirm" class="text-secondary">비밀번호</label>
      </div>
      <hr>
      <button class="w-100 btn btn-lg btn-primary" type="submit">회원가입</button>
    </form>
    <hr>
    <p class="text-center text-muted mt-2 mb-0">이미 계정이 있으신가요?
      <a href="/login" class="fw-bold text-body"><u>로그인</u></a></p>
  </div>
</template>

<script>
import joinApi from '@/apis/join';
import {useRouter} from "vue-router";
import {ref} from "vue";

export default {
  setup() {
    const router = useRouter();
    
    let email = sessionStorage.getItem('email');
    console.log("email: ", email)
    if (!email) {
      router.push('/join/mail');
      return; // email이 없으면 함수 종료
    }

    let loginId = ref('');
    let nickname = ref('');
    let password = ref('');
    let passwordConfirm = ref('');

    const joinUser = async () => {
      try {
        const formData = new FormData();
        formData.append('loginId', loginId.value);
        formData.append('nickname', nickname.value);
        formData.append('password', password.value);
        formData.append('passwordConfirm', passwordConfirm.value);
        formData.append('gitEmail', email);
        // 파일을 FormData에 추가
        const fileInput = document.getElementById('multipartFile');
        if (fileInput.files.length > 0) {
          formData.append('multipartFile', fileInput.files[0]);
        }
        const response = await joinApi.postJoin(formData);
        if (response.data.isSuccess) {
          router.push('/login');
          sessionStorage.removeItem('email');
        } else {
          alert(response.data.message);
        }
      } catch (e) {
        console.log("joinUser error, ", e)
      }
    }

    function displaySelectedImage(event) {
      const selectedImage = document.getElementById('selectedAvatar');
      selectedImage.style.display = 'block';
      selectedImage.src = URL.createObjectURL(event.target.files[0]);
    }

    return {
      displaySelectedImage,
      email,
      loginId,
      nickname,
      password,
      passwordConfirm,
      joinUser
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