<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px; margin-left: 5px" suffix-icon="el-icon-search" placeholder="请输入名称" v-model="name"></el-input>
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
        stripe row-key="id" default-expand-all
        @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="50"/>
      <el-table-column prop="menuName" label="菜单名" width="110"/>
      <el-table-column prop="path" label="路径" width="100"/>
      <el-table-column prop="component" label="组件" width="140"/>
      <el-table-column label="图标" align="center">
        <template slot-scope="scope">
          <i :class="scope.row.icon" style="font-size: 20px"/>
        </template>
      </el-table-column>
      <el-table-column prop="description" label="描述" align="center"/>
      <el-table-column prop="option" label="操作" align="center">
        <template slot-scope="scope">
          <el-button type="primary" @click="handleAddC(scope.row.id)" v-if="!scope.row.pid">新增子菜单<i class="el-icon-plus" style="margin-left: 5px"></i></el-button>
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
    <el-dialog title="菜单信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form :model="form" label-width="100px" size="small">
        <el-form-item label="名称">
          <el-input v-model="form.menuName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="路径">
          <el-input v-model="form.path" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="图标">
          <el-select clearable v-model="form.icon" placeholder="请选择" style="width: 100%">
            <el-option v-for="item in options" :key="item.name" :label="item.name" :value="item.value">
              <i :class="item.value"/>{{item.name}}
            </el-option>
          </el-select>
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
  </div>
</template>

<script>
export default {
  name: "MenuView",
  data() {
    return {
      name: "",
      description: "",
      tableData: [],
      multipleSelection: [],
      dialogFormVisible: false,
      form: {}
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      this.axios.get("/menu/all").then(res => {
        let result = res.data
        if (result.code === 'C200') {
          this.tableData = result.data
        } else {
          this.$message.error(result.msg)
        }
      }).catch(error => {
        this.$message.error(error.response.data.msg);
        this.$store.commit("logout");
      })
    },
    search() {
      this.axios.get("/menu/find", {
        params: {
          name: this.name,
          decription: this.description
        }
      }).then(res => {
        let result = res.data
        if (result.code === 'C200') {
          this.tableData = result.data
        } else {
          this.$message.error(result.msg)
        }
      }).catch(error => {
        this.$message.error(error.response.data.msg);
        this.$store.commit("logout");
      })
    },
    reset() {
      this.name = ""
      this.description = ""
      this.load()
    },
    handleAdd() {
      this.dialogFormVisible = true
      this.form = {}
    },
    handleAddC(pid) {
      this.dialogFormVisible = true
      this.form = {}
      this.form.pid = pid
    },
    save() {
      this.axios.post("/menu/saveOrUpdate", this.form).then(res => {
        let result = res.data
        if (result.code === 'C200') {
          this.$message.success("添加成功")
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
    handleSelectionChange(val) {
      console.log(val)
      this.multipleSelection = val
    },
    delBatch() {
      let ids = this.multipleSelection.map(v => v.id)
      this.axios.post("/menu/batchDelete", ids).then(res => {
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
    handleEdit(row) {
      this.form = row
      this.dialogFormVisible = true
    },
    handleDelete(id) {
      this.axios.delete("/menu/delete", {
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
    }
  }
}
</script>

<style>

</style>