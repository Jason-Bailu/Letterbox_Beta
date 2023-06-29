<template>
  <div class="wrapper">
    <div style="margin: 150px auto; background-color: #fff; width: 350px; height: 400px; padding: 20px; border-radius: 10px">
      <div style="margin: 20px 0; text-align: center; font-size: 24px"><b>注册</b></div>
      <el-form :model="user" :rules="rules" ref="userForm">
        <el-form-item prop="username">
          <el-input  size="medium" placeholder="用户名" style="margin: 10px 0" prefix-icon="el-icon-user" v-model="user.username"></el-input>
        </el-form-item>
        <el-form-item prop="nickname">
          <el-input  size="medium" placeholder="昵称" style="margin: 10px 0" prefix-icon="el-icon-user" v-model="user.nickname"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input size="medium" placeholder="密码" style="margin: 10px 0" prefix-icon="el-icon-lock" show-password v-model="user.password"></el-input>
        </el-form-item>
        <el-form-item prop="confirmPassword">
          <el-input size="medium" placeholder="确认密码" style="margin: 10px 0" prefix-icon="el-icon-lock" show-password v-model="user.confirmPassword"></el-input>
        </el-form-item>
        <el-form-item style="margin: 10px 0; text-align: right">
          <el-button type="primary" size="small" autocomplete="off" @click="register">确定</el-button>
          <el-button type="warning" size="small" autocomplete="off" @click="$router.push('/login')">登录</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: "RegisterView",
  data() {
    return {
      user: {},
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 4, max: 10, message: '长度在 4 到 10 个字符', trigger: 'blur' }
        ],
        nickname: [
          { required: true, message: '请输入昵称', trigger: 'blur' },
          { min: 4, max: 10, message: '长度在 4 到 10 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, max: 10, message: '长度在 6 到 10 个字符', trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, message: '请输入确认密码', trigger: 'blur' },
          { min: 6, max: 10, message: '长度在 6 到 10 个字符', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    register() {
      this.$refs['userForm'].validate((valid) => {
        if (valid) {
          if (this.user.password != this.user.confirmPassword) {
            this.$message.error("密码不一致");
            return false;
          }
          this.axios.post("/user/register", this.user).then(res => {
            let result = res.data;
            if (result.code === 'C200') {
              this.$message.success("注册成功");
              this.$router.push("/login");
            } else if (result.code === 'C400') {
              this.$message.error(result.msg);
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
      })
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
