<template>
  <div style="line-height: 60px; display: flex">
    <div style="flex: 1; text-align: left">
      <span :class="collapseBtnClass" style="cursor: pointer; font-size: 18px" @click="collapse"></span>
      <el-breadcrumb separator="/" style="display: inline-block; margin-left: 10px">
        <el-breadcrumb-item :to="{ path: '/' }">管理页面</el-breadcrumb-item>
        <el-breadcrumb-item>{{this.$route.name}}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div style="text-align: right; height: 60px; width: 150px; cursor: pointer">
      <el-dropdown>
        <div style="display: inline-block;">
          <span class="block">
            <el-avatar style="margin-top: 10px" shape="square" :size="40" :src="user.avatar"></el-avatar>
          </span>
        </div>
        <el-dropdown-menu slot="dropdown" style="width: 100px; text-align: center">
          <el-dropdown-item style="font-size: 14px; padding: 5px 0">
            <router-link to="/sys/admin" style="text-decoration: none;">个人信息</router-link>
          </el-dropdown-item>
          <el-dropdown-item style="font-size: 14px; padding: 5px 0">
            <el-button type="text" @click="openPasswordTable">修改密码</el-button>
          </el-dropdown-item>
          <el-dropdown-item style="font-size: 14px; padding: 5px 0">
            <span style="text-decoration: none;" @click="logout">退出</span>
          </el-dropdown-item>
      </el-dropdown-menu>
      <el-dialog width="30%" :visible.sync="dialogFormVisible">
            <div slot="title" style="text-align: center; font-weight: bold; height: 60px; line-height: 60px">修改密码</div>
            <el-form :model="form" style="text-align: left;" label-width="100px" size="small">
              <el-form-item label="原密码">
                <el-input v-model="form.password" show-password autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="新密码" >
                <el-input v-model="form.newPassword" show-password autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="确认密码">
                <el-input v-model="form.confirmPassword" show-password autocomplete="off"></el-input>
              </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer" style="height: 70px; line-height: 70px">
              <el-button @click="dialogFormVisible = false">取 消</el-button>
              <el-button type="primary" @click="changePassword">确 定</el-button>
            </div>
          </el-dialog>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
export default {
  name: "HeaderBar",
  data() {
    return {
      form: {},
      dialogFormVisible: false
    }
  },
  props: {
    collapseBtnClass: String,
    collapse: Function,
    user: Object
  },
  methods: {
    logout() {
      this.axios.post("/logout").then(res => {
        let result = res.data;
        if (result.code === 'C200') {
          this.$store.commit("logout");
          this.$message.success(result.msg);
        }
      })
    },
    openPasswordTable() {
      this.dialogFormVisible = true;
    },
    changePassword() {
      if (this.form.newPassword != this.form.confirmPassword) {
        this.$message.error("密码不一致！");
        return false;
      }
      if (this.form.newPassword === this.form.password) {
        this.$message.error("与原密码相同");
        return false;
      }
      this.axios.post("/user/changePassword", {
        username: this.user.username,
        password: this.form.password,
        newPassword: this.form.newPassword
      }).then(res => {
        let result = res.data;
        if (result.code === 'C200') {
          this.$message.success("修改成功");
          this.logout();
        } else if (result.code === 'C400') {
          this.$message.error(result.msg);
        } else {
          this.$message.error("服务异常，请稍后重试");
          this.$store.commit("logout");
        }
      })
    }
  }
}
</script>

<style>

</style>
