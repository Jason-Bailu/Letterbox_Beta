<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px; margin-left: 5px" suffix-icon="el-icon-search" placeholder="请输入名称" v-model="roleName"></el-input>
      <el-input style="width: 200px; margin-left: 5px" suffix-icon="el-icon-document" placeholder="请输入描述" v-model="description"></el-input>
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
      <el-table-column type="selection" width="55"/>
      <el-table-column prop="roleName" label="角色名"/>
      <el-table-column prop="level" label="等级"/>
      <el-table-column prop="description" label="描述"/>
      <el-table-column prop="menus" label="权限配置" align="center">
        <template slot-scope="scope">
          <el-button type="info" @click="handleMenu(scope.row.id)">分配菜单/权限<i class="el-icon-menu"></i></el-button>
        </template>
      </el-table-column>
      <el-table-column prop="option" label="操作">
        <template slot-scope="scope">
          <el-button type="success" @click="handleEdit(scope.row)">编辑</el-button>
          <el-popconfirm
              confirm-button-text='好的'
              cancel-button-text='不用了'
              icon="el-icon-info"
              icon-color="red"
              title="确定删除吗？"
              @confirm="handleDelete(scope.row.id)">
            <el-button type="danger" slot="reference" style="margin-left: 5px">删除</el-button>
          </el-popconfirm>
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
    <el-dialog title="角色信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form :model="form" label-width="100px" size="small">
        <el-form-item label="名称">
          <el-input v-model="form.roleName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="等级">
          <el-input v-model="form.level" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="form.description" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog title="菜单分配" :visible.sync="dialogMenuVisible" width="30%">
      <el-tree
          :data="menuData"
          show-checkbox
          node-key="id"
          ref="tree"
          :default-expanded-keys="expends"
          :default-checked-keys="checkeds"
          :props="defaultProps">
        <span class="custom-tree-node" slot-scope="{data}">
          <span><i :class="data.icon"/>{{ data.menuName }}</span>
        </span>
      </el-tree>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogMenuVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveRoleMenu">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "RoleView",
  data() {
    return {
      roleName: "",
      description: "",
      tableData: [],
      pageNum: 1,
      pageSize: 5,
      total: 0,
      dialogFormVisible: false,
      dialogMenuVisible: false,
      form: {},
      multipleSelection: [],
      menuData: [],
      defaultProps: {
        label: 'name',
        children: 'children'
      },
      roleId: null,
      expends: [],
      checkeds: []

    }
  },
  created() {
    this.load();
  },
  methods: {
    load() {
      this.axios.get("role/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize
        }
      }).then(res => {
        let result = res.data
        if (result.code === 'C200') {
          this.tableData = result.data.page
          this.total = result.data.total
        } else (
          this.$message.error(result.msg)
        )
      }).catch(error => {
        this.$message.error(error.response.data.msg);
        this.$store.commit("logout");
      })
    },
    search() {
      this.axios.get("/role/find", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          roleName: this.roleName,
          description: this.description
        }
      }).then(res => {
        let result = res.data
        if (result.code === 'C200') {
          this.tableData = result.data.page
          this.total = result.data.total
        } else {
          this.$message.error(result.msg)
        }
      }).catch(error => {
        this.$message.error(error.response.data.msg);
        this.$store.commit("logout");
      })
    },
    reset() {
      this.total = 0
      this.pageNum = 1
      this.pageSize = 5
      this.roleName = ""
      this.description = ""
      this.load()
    },
    handleAdd() {
      this.dialogFormVisible = true
      this.form = {}
    },
    save() {
      this.axios.post("/role/save", this.form).then(res => {
        let result = res.data;
        if (result.code === 'C200') {
          this.$message.success("保存成功")
          this.dialogFormVisible = false
          this.load()
        } else {
          this.$message.error(result.msg)
        }
      }).catch(error => {
        this.$message.error(error.response.data.msg);
        this.$store.commit("logout");
      })
    },
    saveRoleMenu() {
      this.axios.post("/role/roleMenu/"+this.roleId, this.$refs.tree.getCheckedKeys()).then(res => {
        let result = res.data
        if (result.code === 'C200') {
          this.$message.success("绑定成功")
          this.dialogMenuVisible = false
          this.load()
        } else {
          this.$message.error("设置失败")
        }
      }).catch(error => {
        this.$message.error(error.response.data.msg);
        this.$store.commit("logout");
      })
    },
    handleMenu(id) {
      this.roleId = id
      this.axios.get("/menu/all").then(res => {
        let result = res.data
        this.menuData = result.data
        this.expends = this.menuData.map(v => v.id)
      }).catch(error => {
        this.$message.error(error.response.data.msg);
      })
      this.axios.get("/role/roleMenu/+"+this.roleId).then(res => {
        this.checkeds = res.data.data
      }).catch(error => {
        this.$message.error(error.response.data.msg);
        this.$store.commit("logout");
      })
      this.dialogMenuVisible = true
    },
    handleEdit(row) {
      this.form = Object.assign({}, row)
      this.dialogFormVisible = true
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    delBatch() {
      let ids = this.multipleSelection.map(v => v.id)
      this.axios.post("/role/batchDelete", ids).then(res => {
        let result = res.data
        if (result.code === 'C200') {
          this.$message.success("批量删除成功")
          this.load()
        } else {
          this.$message.error(result.msg)
        }
      }).catch(error => {
        this.$message.error(error.response.data.msg);
        this.$store.commit("logout");
      })
    },
    handleDelete(id) {
      this.axios.delete("/role/delete", {
        params: {
          id: id
        }
      }).then(res => {
        let result = res.data
        if (result.code === 'C200') {
          this.$message.success("删除成功")
          this.load()
        } else {
          this.$message.error(result.msg)
        }
      }).catch(error => {
        this.$message.error(error.response.data.msg);
        this.$store.commit("logout");
      })
    },
    handleSizeChange(pageSize) {
      console.log(pageSize);
      this.pageSize = pageSize;
      if (this.roleName != "" || this.description != "") {
        this.search();
      } else {
        this.load();
      }
    },
    handleCurrentChange(pageNum) {
      console.log(pageNum);
      this.pageNum = pageNum;
      if (this.roleName != "" || this.description != "") {
        this.search();
      } else {
        this.load();
      }
    }
  }
}
</script>

<style>

</style>
