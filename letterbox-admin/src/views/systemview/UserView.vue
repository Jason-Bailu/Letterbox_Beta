<template>
  <div>
    <div style="margin-bottom: 10px; height: 60px; line-height: 60px">
      <el-input style="width: 200px; margin-left: 5px" suffix-icon="el-icon-search" placeholder="请输入名称" v-model="username"></el-input>
      <el-input style="width: 200px; margin-left: 5px" suffix-icon="el-icon-user" placeholder="请输入昵称" v-model="nickname"></el-input>
      <el-input style="width: 200px; margin-left: 5px" suffix-icon="el-icon-position" placeholder="请输入地址" v-model="address"></el-input>
      <el-button style="margin-left: 5px" type="primary" @click="search">搜索</el-button>
      <el-button style="margin-left: 5px" type="warning" @click="reset">重置</el-button>
      <el-button style="margin-left: 5px" type="primary" @click="handleAdd">新增<i class="el-icon-circle-plus-outline" style="margin-left: 5px"></i></el-button>
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
      <el-table-column type="selection" width="50"/>
      <el-table-column prop="username" label="用户名"/>
      <el-table-column prop="nickname" label="昵称"/>
      <el-table-column prop="email" label="邮箱"/>
      <el-table-column prop="phone" label="手机"/>
      <el-table-column prop="address" label="地址"/>
      <el-table-column prop="avatar" label="头像">
        <template slot-scope="scope">
          <el-avatar :src="scope.row.avatar"></el-avatar>
        </template>
      </el-table-column>
      <el-table-column prop="roleName" label="角色"/>
      <el-table-column prop="enable" label="启用">
        <template slot-scope="scope">
          <div v-if="scope.row.id==1">
            <el-switch v-model="scope.row.enable" active-color="#13ce66" disabled inactive-color="#ccc" @change="enable(scope.row)"></el-switch>
          </div>
          <div v-else>
            <el-switch v-model="scope.row.enable" active-color="#13ce66" inactive-color="#ccc" @change="enable(scope.row)"></el-switch>
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="option" label="操作" align="center">
        <template slot-scope="scope">
          <div v-if="scope.row.id==1">
            <el-button type="success" disabled @click="handleEdit(scope.row)">编辑</el-button>
            <el-popconfirm
                confirm-button-text='好的'
                cancel-button-text='不用了'
                icon="el-icon-info"
                icon-color="red"
                title="确定删除吗？"
                @confirm="handleDelete(scope.row.id)">
              <el-button type="danger" slot="reference" disabled style="margin-top: 5px">删除</el-button>
            </el-popconfirm>
          </div>
          <div v-else>
            <el-button type="success" @click="handleEdit(scope.row)">编辑</el-button>
            <el-popconfirm
                confirm-button-text='好的'
                cancel-button-text='不用了'
                icon="el-icon-info"
                icon-color="red"
                title="确定删除吗？"
                @confirm="handleDelete(scope.row.id)">
              <el-button type="danger" slot="reference" style="margin-top: 5px">删除</el-button>
            </el-popconfirm>
          </div>
        </template>
      </el-table-column>
    </el-table>
    <div style="padding: 10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-size="pageSize"
          :page-sizes="[2, 5, 10, 15]"
          :total="total"
          layout="total, sizes, prev, pager, next, jumper">
      </el-pagination>
    </div>
    <el-dialog id="user-dialog" :visible.sync="dialogFormVisible" width="30%">
      <div slot="title" style="height: 60px; line-height: 60px">用户信息</div>
      <el-form :model="form" label-width="100px" size="small">
        <el-form-item label="用户名">
          <el-input v-model="form.username" autocomplete="off"></el-input>
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
        <el-form-item label="角色">
          <el-select clearable v-model="form.roleName" placeholder="请选择角色" style="width: 100%">
            <el-option v-for="item in roles" :key="item.roleName" :label="item.roleName" :value="item.roleName"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer" style="height: 70px; line-height: 70px">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "UserView",
  data() {
    return {
      username: "",
      nickname: "",
      address: "",
      tableData: [],
      pageNum: 1,
      pageSize: 5,
      total: 0,
      form: {
        password: 123456
      },
      dialogFormVisible: false,
      multipleSelection: [],
      roles: []
    }
  },
  created() {
    this.load();
  },
  methods: {
    load() {
      this.axios.get("/user/page",{
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize
        }
      }).then(res => {
        let result = res.data;
        if (result.code === 'C200') {
          this.total = result.data.total;
          this.tableData = result.data.page;
        } else {
          this.$message.error(result.msg);
        }
      }).catch(error => {
        this.$message.error(error.response.data.msg);
        this.$store.commit("logout");
      })
      this.axios.get("/role/all").then(res => {
        let result = res.data;
        if (result.code === 'C200') {
          this.roles = result.data
        } else {
          this.$message.error(result.msg)
        }
      }).catch(error => {
        this.$message.error(error.response.data.msg);
        this.$store.commit("logout");
      })
    },
    search() {
      this.axios.get("/user/find", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          username: this.username,
          nickname: this.nickname,
          address: this.address
        }
      }).then(res => {
        let result = res.data;
        if (result.code === 'C200') {
          this.total = result.data.total;
          this.tableData = result.data.page;
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
      this.username = "";
      this.nickname = "";
      this.address = "";
      this.load();
    },
    handleAdd() {
      this.dialogFormVisible = true;
      this.form = {};
    },
    handleSelectionChange(val) {
      console.log(val);
      this.multipleSelection = val;
    },
    delBatch() {
      let ids = this.multipleSelection.map(v => v.id);
      this.axios.post("/user/batchDelete", ids).then(res => {
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
    enable(user) {
      this.axios.post("/user/enable", user).then(res => {
        let result = res.data
        if (result.code === 'C200') {
          this.$message.success(result.msg)
          this.load()
        } else {
          this.$message.error("禁用失败")
        }
      }).catch(error => {
        this.$message.error(error.response.data.msg);
        this.$store.commit("logout");
      })
    },
    handleEdit(user) {
      this.form = Object.assign({}, user);
      this.dialogFormVisible = true;
    },
    save() {
      this.axios.post("/user/save", this.form).then(res => {
        let result = res.data;
        if (result.code === 'C200') {
          this.$message.success(result.msg);
          this.dialogFormVisible = false;
          this.load();
        } else {
          this.$message.error(result.msg);
        }
      }).catch(error => {
        this.$message.error(error.response.data.msg);
        this.$store.commit("logout");
      })
    },
    handleDelete(id) {
      this.axios.delete("/user/delete/"+id).then(res => {
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
    },
    handleSizeChange(pageSize) {
      console.log(pageSize);
      this.pageSize = pageSize;
      this.load();
    },
    handleCurrentChange(pageNum) {
      console.log(pageNum);
      this.pageNum = pageNum;
      this.load();
    }
  }
}
</script>

<style>
</style>
