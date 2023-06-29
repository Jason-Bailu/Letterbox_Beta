<template>
  <div>
    <div style="margin: 5px 0px 10px 0;">
      <el-input style="width: 200px; margin-left: 5px" suffix-icon="el-icon-search" disabled placeholder="请输入用户名" v-model="username"></el-input>
      <el-button style="margin-left: 5px" type="primary" @click="search">搜索</el-button>
      <el-button style="margin-left: 5px" type="warning" @click="reset">重置</el-button>
      <el-button style="margin-left: 5px" type="primary" @click="newLetter">新建</el-button>
    </div>
    <el-table
    :data="tableData"
    :default-sort = "{prop: 'date', order: 'descending'}"
    height="60vh"
    style="width: 100%; border-radius: 10px">
      <el-table-column prop="date" label="日期" fixed="left" sortable width="100" align="center"></el-table-column>
      <el-table-column label="配送信息" align="center">
        <el-table-column prop="username" label="作者" width="60" align="center"></el-table-column>
        <el-table-column prop="callPhone" label="电话" width="100" align="center"></el-table-column>
        <el-table-column prop="receiver" label="收件方" width="60" align="center"></el-table-column>
        <el-table-column prop="receiverPhone" label="收件电话" width="100" align="center"></el-table-column>
        <el-table-column label="地址配置" align="center">
          <el-table-column prop="fromAddress" label="源地址" width="300"></el-table-column>
          <el-table-column prop="toAddress" label="目的地址" width="300"></el-table-column>
          <el-table-column prop="postalCode" label="邮编" width="120" align="center"></el-table-column>
        </el-table-column>
      </el-table-column>
      <el-table-column label="配置内容" align="center">
        <el-table-column label="信纸" align="center">
          <template slot-scope="scope">
            <el-tag type="info">{{ scope.row.goods[0].detail }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="信封" align="center">
          <template slot-scope="scope">
            <el-tag type="info">{{ scope.row.goods[1].detail }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="inkId" label="字体" align="center">
          <template slot-scope="scope">
            <el-tag type="info">{{ scope.row.goods[2].detail }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="内容" align="center">
          <template slot-scope="scope">
            <el-button type="text" @click="handleView(scope.row)">预览</el-button>
          </template>
        </el-table-column>
      </el-table-column>
      <el-table-column label="状态" fixed="right" align="center">
        <template slot-scope="scope">
          <div v-if="scope.row.status==0">
            <el-tag type="info">未支付</el-tag>
          </div>
          <div v-else-if="scope.row.status===1">
            <el-tag type="info">已支付</el-tag>
          </div>
          <div v-else-if="scope.row.status===2">
            <el-tag type="info">未发出</el-tag>
          </div>
          <div v-else-if="scope.row.status===3">
            <el-tag type="info">已发出</el-tag>
          </div>
          <div v-else-if="scope.row.status===4">
            <el-tag type="info">已退回</el-tag>
          </div>
          <div v-else-if="scope.row.status===5">
            <el-tag type="info">已收到</el-tag>
          </div>
          <div v-else>
            <el-tag type="info">none</el-tag>
          </div>
        </template>
      </el-table-column>
    </el-table>
    <div style="padding: 10px 0; margin-top: 10px;">
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
    <el-dialog
        title="预览"
        :visible.sync="textDialogVisible"
        width="40%"
        top="5vh"
        center>
      <el-descriptions title="信件" class="margin-top" :column="3" size="mini" border>
        <el-descriptions-item label="用户名">{{ letter.username }}</el-descriptions-item>
        <el-descriptions-item label="联系方式">{{ letter.callPhone }}</el-descriptions-item>
        <el-descriptions-item label="收件方">{{ letter.receiver }}</el-descriptions-item>
        <el-descriptions-item label="收件电话">{{ letter.receiverPhone }}</el-descriptions-item>
        <el-descriptions-item label="源地址">{{ letter.fromAddress }}</el-descriptions-item>
        <el-descriptions-item label="目的地址">{{ letter.toAddress }}</el-descriptions-item>
        <el-descriptions-item label="邮编">{{ letter.postalCode }}</el-descriptions-item>
      </el-descriptions>
      <el-divider>内容</el-divider>
      <div v-if="letter.inkId===13">
        <span class="zh_CN_1">{{ letter.context }}</span>
      </div>
      <div v-else-if="letter.inkId===14">
        <span class="zh_CN_2">{{ letter.context }}</span>
      </div>
      <div v-else-if="letter.inkId===15">
        <span class="zh_CN_3">{{ letter.context }}</span>
      </div>
      <div v-else-if="letter.inkId===16">
        <span class="en_US_4">{{ letter.context }}</span>
      </div>
      <div v-else-if="letter.inkId===17">
        <span class="en_US_5">{{ letter.context }}</span>
      </div>
      <div v-else-if="letter.inkId===18">
        <span class="en_US_6">{{ letter.context }}</span>
      </div>
      <div v-else>
        <span>{{ letter.context }}</span>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="download(letter.url)">下 载</el-button>
        <el-button type="primary" @click="textDialogVisible = false">关 闭</el-button>
      </span>
    </el-dialog>
    <el-drawer
    title="新建信件"
    :before-close="handleClose"
    :visible.sync="formDialogVisible"
    direction="rtl"
    size="50%"
    custom-class="demo-drawer"
    ref="drawer"
    >
      <div class="drawer__content">
        <el-form :model="form" label-position="left">
          <el-row :gutter="28">
            <el-col>
              <el-form-item label="内容" style="margin: 10px 10px 10px 10px;">
                <el-input type="textarea" resize="none" :rows="10" v-model="form.context" autocomplete="off"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-divider></el-divider>
          <el-row :gutter="28">
            <el-col :span="12">
              <el-form-item label="用户名" label-width="100px" style="margin: 10px 10px 10px 10px;">
                <el-input v-model="form.username" autocomplete="off"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="联系方式" label-width="100px" style="margin: 10px 10px 10px 10px;">
                <el-input v-model="form.callPhone" autocomplete="off"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="28">
            <el-col :span="12">
              <el-form-item label="收件方" label-width="100px" style="margin: 10px 10px 10px 10px;">
                <el-input v-model="form.receiver" autocomplete="off"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="联系方式" label-width="100px" style="margin: 10px 10px 10px 10px;">
                <el-input v-model="form.receiverPhone" autocomplete="off"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-divider></el-divider>
          <el-row :gutter="28">
            <el-col>
              <el-form-item label="源地址" label-width="75px" style="margin: 10px 10px 10px 10px;">
                <el-input v-model="form.fromAddress" autocomplete="off" placeholder="电子信件默认为邮箱"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="28">
            <el-col>
              <el-form-item label="目的地址" label-width="75px" style="margin: 10px 10px 10px 10px;">
                <el-input v-model="form.toAddress" autocomplete="off" placeholder="电子信件默认为邮箱"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="10">
            <el-col :span="10">
              <el-form-item label="邮编" label-width="75px" style="margin: 10px 10px 10px 10px;">
                <el-input v-model="form.postalCode" autocomplete="off"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <div class="demo-drawer__footer">
          <el-button @click="cancelForm">取 消</el-button>
          <el-button type="primary" @click="$refs.drawer.closeDrawer()" :loading="loading">{{ loading ? '提交中 ...' : '确 定' }}</el-button>
        </div>
      </div>
    </el-drawer>
  </div>
</template>

<script>
import { getToken } from '@/utils/auth';
import Cookie from 'js-cookie';

export default {
  name: "TextView",
  data() {
    return {
      username: Cookie.get("username"),
      tableData: [],
      pageNum: 1,
      pageSize: 10,
      total: 0,
      textDialogVisible: false,
      loading: false,
      formDialogVisible: false,
      timer: null,
      letter: {},
      token: {
        Authorization: getToken()
      },
      form: {
        status: 2
      }
    }
  },
  created() {
    this.load();
  },
  methods: {
    load() {
      this.axios.get("/letter/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          username: this.username
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
      this.axios.get("/letter/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          username: this.username
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
      this.username = Cookie.get("username");
      this.total = 0;
      this.pageNum = 1;
      this.pageSize = 10;
      this.load();
    },
    newLetter() {
      this.formDialogVisible = true;
    },
    handleView(data) {
      this.textDialogVisible = true;
      this.letter = data;
    },
    download(url) {
      window.open(url);
    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize;
      this.load();
    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum;
      this.load();
    },
    handleClose() {
      if (this.loading) {
        return;
      }
      this.$confirm('确定要提交表单吗？')
        .then(_ => {
          this.loading = true;
          this.axios.post("/letter/add", this.form).then(res => {
            let result = res.data;
            if (result.code === 'C200') {
              this.$message.success("添加成功");
              this.load();
            } else {
              this.$message.error(result.msg);
            }
          }).catch(error => {
            this.$message.error(error.response.data.msg);
            this.$store.commit("logout");
          })
          this.timer = setTimeout(() => {
            // 动画关闭需要一定的时间
            setTimeout(() => {
              this.loading = false;
            }, 400);
          }, 2000);
          this.formDialogVisible = false;
        })
        .catch(_ => {});
    },
    cancelForm() {
      this.loading = false;
      this.formDialogVisible = false;
      clearTimeout(this.timer);
    }
  }
}
</script>

<style scoped lang="less">
@import "../../assets/font/font.css";

.zh_CN_1 {
  font-family: coki;
  font-size: 25px;
}

.zh_CN_2 {
  font-family: WCF;
  font-size: 25px;
}

.zh_CN_3 {
  font-family: WF;
  font-size: 25px;
}

.en_US_4 {
  font-family: willion;
  font-size: 25px;
}

.en_US_5 {
  font-family: family;
  font-size: 25px;
}

.en_US_6 {
  font-family: romeobohemian;
  font-size: 25px;
}
</style>
