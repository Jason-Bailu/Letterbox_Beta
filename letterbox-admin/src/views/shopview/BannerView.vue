<template>
  <div>
    <el-row type="flex">
      <el-col>
        <el-carousel indicator-position="outside" height="60vh">
          <el-carousel-item v-for="item in fileList" :key="item.id">
            <el-image 
              style="width: 100%; height: 60vh"
              :src="item.url"
              fit="fill"
              :preview-src-list="srcList">
            </el-image>
          </el-carousel-item>
        </el-carousel>
      </el-col>
    </el-row>
    <el-row type="flex">
      <el-col>
        <el-upload
          class="upload-demo"
          action="http://ip:port/letterbox-api/banner/add"
          :headers="token"
          multiple
          :limit="6"
          :before-remove="beforeRemove"
          :on-remove="handleRemove"
          :on-success="handleUploadSuccess"
          :file-list="fileList">
          <el-button size="small" type="primary">点击上传</el-button>
          <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
        </el-upload>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { getToken } from '@/utils/auth';

export default {
  name: "BannerView",
  data() {
    return {
      token: {
        Authorization: getToken()
      },
      fileList: [],
      srcList: []
    }
  },
  created() {
    this.load();
  },
  methods: {
    load() {
      this.axios.get("/banner/all").then(res => {
        let result = res.data;
        if (result.code === 'C200') {
          this.fileList = result.data;
          this.fileList.forEach(item => {
            this.srcList.push(item.url);
          })
        } else {
          this.$message.error(result.msg);
        }
      }).catch(error => {
        this.$message.error(error.response.data.msg);
        this.$store.commit("logout");
      })
    },
    handleUploadSuccess() {
      this.load();
    },
    beforeRemove(file) {
      return this.$confirm(`确定移除 ${ file.name }？`);
    },
    handleRemove(file, fileList) {
      this.axios.post("/banner/delete/"+file.id).then(res => {
        let result = res.data;
        if (result.code === 'C200') {
          this.$message.success(result.msg);
          this.load();
        } else {
          this.$message.error(result.msg);
        }
      }).catch(error => {
        this.$message.error(error.response.data.msg);
        this.$store.commit("logout");
      })
    }
  }
}
</script>

<style>

</style>
