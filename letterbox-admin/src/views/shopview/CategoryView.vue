<template>
  <div>
    <el-table
        :data="categoryData"
        style="width: 100%; border-radius: 10px">
        <el-table-column prop="className" label="类别" align="center"/>
        <el-table-column label="图标" align="center">
            <template slot-scope="scope">
            <i :class="scope.row.icon" style="font-size: 20px"/>
            </template>
        </el-table-column>
        <el-table-column prop="count" label="数量" align="center"/>
        <el-table-column prop="option" label="操作" align="center">
            <template  slot-scope="scope">
                <el-button type="success" @click="handleCheck(scope.row)">查看</el-button>
                <el-button type="success" @click="handleAdd(scope.row)">添加</el-button>
            </template>
        </el-table-column>
    </el-table>
    <el-divider></el-divider>
    <el-card class="box-card">
        <el-table
            :data="tableData"
            height="60vh"
            style="width: 100%; border-radius: 10px">
            <el-table-column prop="goodName" label="名称" align="center"/>
            <el-table-column prop="detail" label="描述" align="center"/>
            <el-table-column prop="className" label="类别" align="center"/>
            <el-table-column prop="repository" label="库存" align="center"></el-table-column>
            <el-table-column label="图片" align="center">
                <template slot-scope="scope">
                    <el-image 
                        style="width: 100px; height: 100px"
                        :src="scope.row.imageUrl" 
                        :preview-src-list="[scope.row.imageUrl]">
                    </el-image>
                </template>
            </el-table-column>
            <el-table-column label="操作" align="center">
                <template  slot-scope="scope">
                    <el-button type="success" @click="handleEdit(scope.row)">编辑</el-button>
                    <el-popconfirm
                        confirm-button-text='好的'
                        cancel-button-text='不用了'
                        icon="el-icon-info"
                        icon-color="red"
                        title="确定删除吗？"
                        @confirm="handleDelete(scope.row)">
                        <el-button slot="reference" style="margin-left: 5px" type="danger">删除</el-button>
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
                :page-sizes="[5, 10, 15]"
                :total="total"
                layout="total, sizes, prev, pager, next, jumper">
            </el-pagination>
        </div>
    </el-card>
    <el-dialog
            title="更新"
            :visible.sync="dialogVisible"
            width="50%"
            center>
            <el-form :model="form" label-width="100px" size="small">
            <el-row :gutter="30">
                <el-col :span="20">
                    <el-form-item label="图片">
                        <el-upload
                            class="avatar-uploader"
                            action="http://ip:port/letterbox-api/file/upload"
                            :show-file-list="false"
                            :headers="token"
                            :on-success="handleImageSuccess">
                            <el-image v-if="form.imageUrl" :src="form.imageUrl" style="width: 200px; height: 200px;" fit="fill"/>
                            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                        </el-upload>
                    </el-form-item>
                </el-col>
                <el-col :span="10">
                    <el-form-item label="ID" v-show="false">
                        <el-input v-model="form.id" autocomplete="off"></el-input>
                    </el-form-item>
                        <el-form-item label="名称">
                    <el-input v-model="form.goodName" autocomplete="off"></el-input>
                        </el-form-item>
                    <el-form-item label="描述">
                        <el-input v-model="form.detail" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="类别">
                        <el-select clearable v-model="form.className" placeholder="请选择类别" style="width: 100%">
                            <el-option v-for="item in categoryData" :key="item.className" :label="item.className" :value="item.className"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="库存">
                        <div v-if="form.className == 'ink'">
                            <el-input-number v-model="form.repository" :min="1" :max="1" :disabled="true"></el-input-number></div>
                        <div v-else-if="form.goodName == 'electronic'">
                            <el-input-number v-model="form.repository" :min="1"  :max="1" :disabled="true"></el-input-number></div>
                        <div v-else-if="form.goodName == 'electroenve'">
                            <el-input-number v-model="form.repository" :min="1"  :max="1" :disabled="true"></el-input-number></div>
                        <div v-else>
                            <el-input-number v-model="form.repository" :min="0" :max="99"></el-input-number>
                        </div>
                    </el-form-item>
                </el-col>
            </el-row>
        </el-form>
        <span slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="handleUpdate">确 定</el-button>
        </span>
    </el-dialog>
  </div>
</template>

<script>
import { getToken } from '@/utils/auth';

export default {
    name: "CategoryView",
    data() {
        return {
            categoryData: [],
            categoryName: "",
            tableData: [],
            pageNum: 1,
            pageSize: 5,
            total: 0,
            dialogVisible: false,
            form: {},
            token: {
                Authorization: getToken()
            }
        }
    },
    created() {
        this.load();
    },
    methods: {
        load() {
            this.axios.get("/category/all").then(res => {
                let result = res.data;
                if (result.code === 'C200') {
                    this.categoryData = result.data;
                } else {
                    this.$message.error(result.msg);
                }
            }).catch(error => {
                this.$message.error(error.response.data.msg);
            })
            this.axios.get("/good/page", {
                params: {
                    categoryName: this.categoryName,
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
        handleCheck(row) {
            this.categoryName = row.className;
            this.axios.get("/good/find/"+this.categoryName, {
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
        handleAdd(row) {
            this.dialogVisible = true;
            this.form = {};
            this.form.className = row.className;
        },
        handleEdit(row) {
            this.dialogVisible = true;
            this.form = row;
            console.log(this.form.id);
        },
        handleDelete(row) {
            this.axios.post("/good/delete", {
                id: row.id,
                className: row.className
            }).then(res => {
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
        handleImageSuccess(res) {
            this.form.imageUrl = res.data;
        },
        handleUpdate() {
            this.axios.post("/good/update", this.form).then(res => {
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
            this.dialogVisible = false;
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

<style>

</style>