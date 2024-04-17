<template>
  <form @submit.prevent="login">
    <div>
      <div>
        <label for="username">username</label>
        <input type="text" id="username" name="username" v-model="username">
      </div>
      <div>
        <label for="password">password</label>
        <input type="password" id="password" name="password" v-model="password">
      </div>
    </div>
    <button type="submit">로그인</button>
  </form>
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

<style lang="scss" scoped>

</style>