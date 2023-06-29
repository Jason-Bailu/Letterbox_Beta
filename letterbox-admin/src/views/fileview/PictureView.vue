<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px; margin-left: 5px" suffix-icon="el-icon-search" placeholder="请输入图片名" v-model="filename"></el-input>
      <el-button style="margin-left: 5px" type="primary" @click="search">搜索</el-button>
      <el-button style="margin-left: 5px" type="warning" @click="reset">重置</el-button>
      <el-upload action="http://ip:port/letterbox-api/file/upload" :headers="token" style="display: inline-block" :show-file-list="false" accept="xlsx" :on-success="handleFileUploadSuccess">
        <el-button style="margin-left: 5px" type="primary">上传图片<i class="el-icon-top" style="margin-left: 5px"></i></el-button>
      </el-upload>
      <el-popconfirm
          confirm-button-text='好的'
          cancel-button-text='不用了'
          icon="el-icon-info"
          icon-color="red"
          title="确定批量删除吗？"
          @confirm="delBatch">
        <el-button style="margin-left: 5px" type="danger" slot="reference">批量删除<i class="el-icon-remove-outline" style="margin-left: 5px"></i></el-button>
      </el-popconfirm>
    </div>
    <el-table 
        :data="tableData"
        height="60vh"
        style="width: 100%; border-radius: 10px"
        @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="url" label="预览" align="center">
        <template slot-scope="scope">
          <el-image style="width: 100px; height: 100px" :src="scope.row.url" fit="fit"></el-image>
        </template>
      </el-table-column>
      <el-table-column prop="fileName" label="图片名" width="140">
      </el-table-column>
      <el-table-column prop="type" label="图片类型" width="140">
      </el-table-column>
      <el-table-column prop="size" label="图片大小(kb)" width="140">
      </el-table-column>
      <el-table-column prop="url" label="下载" width="140">
        <template slot-scope="scope">
          <el-button type="primary" @click="download(scope.row.url)">下载</el-button>
        </template>
      </el-table-column>
      <el-table-column prop="enable" label="启用" align="center">
        <template slot-scope="scope">
            <el-switch v-model="scope.row.enable" active-color="#13ce66" inactive-color="#ccc" @change="enable(scope.row)"></el-switch>
        </template>
      </el-table-column>
      <el-table-column prop="option" label="操作" align="center">
        <template slot-scope="scope">
          <el-popconfirm
              confirm-button-text='好的'
              cancel-button-text='不用了'
              icon="el-icon-info"
              icon-color="red"
              title="确定删除吗？"
              @confirm="handleDelete(scope.row.id)">
            <el-button type="danger" slot="reference" style="margin-left: 5px">删除<i class="el-icon-delete" style="margin-left: 5px"></i></el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <div style="padding: 10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[2, 5, 10, 15]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>
  </div>
</template>

<script>
import { getToken } from '@/utils/auth';
export default {
  name: "PictureView",
  data() {
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      filename: "",
      token: {
        Authorization: getToken()
      },
      form: {},
      multipleSelection: []
    }
  },
  created() {
    this.load();
  },
  methods: {
    load() {
      this.axios.get("/file/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize
        }
      }).then(res => {
        let result = res.data;
        if (result.code === 'C200') {
          this.tableData = result.data.page;
          this.total = result.data.total;
        } else {
          this.$message.error(result.msg);
        }
      }).catch(error => {
        this.$message.error(error.response.data.msg);
        this.$store.commit("logout");
      })
    },
    search() {
      this.axios.get("/file/find", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          filename: this.filename
        }
      }).then(res => {
        let result = res.data;
        if (result.code === 'C200') {
          this.tableData = result.data.page;
          this.total = result.data.total;
        } else {
          this.$message.error(result.msg);
        }
      }).catch(error => {
        this.$message.error(error.response.data.msg);
        this.$store.commit("logout");
      })
    },
    reset() {
      this.total = 0;
      this.pageNum = 1;
      this.pageSize = 5;
      this.filename = "";
      this.load();
    },
    handleFileUploadSuccess(res) {
      if (res.code === 'C200') {
        this.$message.success("保存成功");
        this.load();
      } else {
        this.$message.error("保存失败");
      }
    },
    delBatch() {
      let ids = this.multipleSelection.map(v => v.id);
      this.axios.post("/file/batchDelete", ids).then(res => {
        let result = res.data;
        if (result.code === 'C200') {
          this.$message.success("批量删除成功");
          this.load();
        } else {
          this.$message.error("批量删除失败");
        }
      }).catch(error => {
        this.$message.error(error.response.data.msg);
        this.$store.commit("logout");
      })
    },
    download(url) {
      window.open(url);
    },
    enable(file) {
      this.axios.post("/file/enable", file).then(res => {
        let result = res.data;
        if (result.code === 'C200') {
          this.$message.success("更新成功");
          this.load();
        } else {
          this.$message.error("更新失败");
        }
      }).catch(error => {
        this.$message.error(error.response.data.msg);
        this.$store.commit("logout");
      })
    },
    handleDelete(id) {
      this.axios.delete("/file/delete", {
        params: {
          id: id
        }
      }).then(res => {
        let result = res.data;
        if (result.code === 'C200') {
          this.$message.success("删除成功");
          this.load();
        } else {
          this.$message.error("删除失败");
        }
      }).catch(error => {
        this.$message.error(error.response.data.msg);
        this.$store.commit("logout");
      })
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize;
      this.load();
    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum;
      this.load();
    }
  }
}
</script>

<style scoped>

</style>
