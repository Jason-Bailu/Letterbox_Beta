<template>
  <div>
    <el-card style="width: 500px; margin: auto; margin-top: 40px;">
      <el-form :model="form" label-width="100px" size="small">
        <el-upload
            class="avatar-uploader"
            action="http://ip:port/letterbox-api/file/upload"
            :headers="token"
            :show-file-list="false"
            :on-success="handleAvatarSuccess">
          <img v-if="form.avatar" :src="form.avatar" class="avatar">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
        <el-form-item label="ID" v-show="false">
          <el-input v-model="form.id" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="用户名">
          <el-input v-model="form.username" disabled autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="form.nickname" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="邮件">
          <el-input v-model="form.email" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="手机">
          <el-input v-model="form.phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="地址">
          <el-input v-model="form.address" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="save">确 定</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import { getToken } from '@/utils/auth';

export default {
  name: "AdminView",
  data() {
    return {
      token: {
        Authorization: getToken()
      },
      form: {},
      user: {}
    }
  },
  created() {
    this.getUser().then(res => {
      this.form = res.data;
    }).catch(error => {
      this.$message.error(error.response.data.msg);
      this.$store.commit("logout");
    })
  },
  methods: {
    async getUser() {
      return (await this.axios.get("/user/local")).data;
    },
    save() {
      this.axios.post("/user/save", this.form).then(res => {
        let result = res.data;
        if (result.code === 'C200') {
          this.$message.success("保存成功");
          this.$emit("refreshUser");
          this.getUser().then(res => {
            this.form = res.data;
          })
        } else {
          this.$message.error("保存失败");
        }
      }).catch(error => {
        this.$message.error(error.response.data.msg);
        this.$store.commit("logout");
      })
    },
    handleAvatarSuccess(res) {
      this.form.avatar = res.data;
    }
  }
}
</script>

<style>
.avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    cursor: pointer;
    position: relative;
    overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 60px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 100%;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>
