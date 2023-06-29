<template>
  <div class="wrapper">
    <div style="margin: 200px auto; background-color: #fff; width: 350px; height: 300px; padding: 20px; border-radius: 10px">
      <div style="margin: 20px 0; text-align: center; font-size: 24px"><b>登录</b></div>
      <el-form :model="user" :rules="rules" :ref="'userForm'">
        <el-form-item prop="username">
          <el-input size="medium" placeholder="请输入用户名" style="margin: 10px 0" v-model="user.username"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input size="medium" placeholder="请输入密码" style="margin: 10px 0" show-password v-model="user.password"></el-input>
        </el-form-item>
        <el-checkbox v-model="user.rememberMe" style="margin: 0px 0px 25px 0px;">记住密码</el-checkbox>
        <el-form-item style="margin: 10px 10px; text-align: right;">
          <el-button type="primary" size="small" autocomplete="off" @click="login">登录</el-button>
          <el-button type="primary" size="small" autocomplete="off" @click="$router.push('/register')">注册</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import { encrypt, decrypt } from '@/utils/jsencrypt';
import Cookie from 'js-cookie';
import router from '@/router';
import { setToken } from '@/utils/auth';

export default {
  name: "LoginView",
  data() {
    return {
      user: {},
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 4, max: 10, message: '长度在 4 到 10 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 4, max: 100, message: '长度在 4 到 100 个字符', trigger: 'blur' }
        ],
      }
    }
  },
  created() {
    this.getCookies()
  },
  methods: {
    login() {
      this.$refs['userForm'].validate(async (valid) => {
        if (valid) {
          //如果勾选了记住密码就要进行加密保存
          if (this.user.rememberMe) {
            Cookie.set("username", this.user.username, {expires: 30})
            Cookie.set("password", encrypt(this.user.password), {expires: 30})
            Cookie.set("rememberMe", this.user.rememberMe, {expires: 30})
          } else {
            Cookie.remove("username")
            Cookie.remove("password")
            Cookie.remove("rememberMe")
          }

          await this.axios.post('/login?username='+this.user.username+'&password='+this.user.password).then(res => {
            let result = res.data;
            if (result.code === 'C200') {
              setToken(result.data.token);
              this.$store.commit("SET_MENUS", result.data.menus);
              this.$message.success(result.msg);
              router.replace('/');
            } else {
              this.$message.error(result.msg);
            }
          }).catch(error => {
            this.$message.error(error.response.data.msg);
            this.$store.commit("logout");
          })
        } else {
          return false;
        }
      });
    },
    getCookies() {
      const username = Cookie.get("username")
      const password = Cookie.get("password")
      const rememberMe = Cookie.get("rememberMe")
      this.user = {
        username: username === undefined ? this.user.username : username,
        password: password === undefined ? this.user.password : decrypt(password),
        rememberMe: rememberMe === undefined ? false : Boolean(rememberMe)
      }
    }
  }
}
</script>

<style>
.wrapper {
  position: absolute;
  width: 100%;
  height: 100%;
  background-image: url("../assets/bg.jpg");
  background-size: 100% 100%;
}
</style>
