<template>
  <el-container style="min-height: 100vh">
    <el-aside :width="sideWidth + 'px'">
      <SideBar :isCollapsed="isCollapsed" :menus="menus"></SideBar>
    </el-aside>
    <el-container>
      <el-header>
        <HeaderBar :collapseBtnClass="collapseBtnClass" :collapse="collapse" :user="user"/>
      </el-header>
      <el-main>
        <router-view @refreshUser="getUser"></router-view>
      </el-main>
      <el-footer>
        <FooterBar></FooterBar>
      </el-footer>
    </el-container>
  </el-container>
</template>

<script>
import HeaderBar from "@/components/HeaderBar";
import SideBar from "@/components/SideBar";
import FooterBar from "@/components/FooterBar";

export default {
  name: "MainView",
  components: {HeaderBar, SideBar, FooterBar},
  data() {
    return {
      isCollapsed: false,
      sideWidth: 200,
      collapseBtnClass: 'el-icon-s-fold',
      user: {},
      menus: {}
    }
  },
  created() {
    this.getUser();
    this.menus = JSON.parse(sessionStorage.getItem("menus"))
  },
  methods: {
    collapse() {
      this.isCollapsed = !this.isCollapsed;
      if (this.isCollapsed) {
        this.sideWidth = 63;
        this.collapseBtnClass = 'el-icon-s-unfold';
      } else {
        this.sideWidth = 200;
        this.collapseBtnClass = 'el-icon-s-fold';
      }
    },
    getUser() {
      this.axios.get("/user/local").then(res => {
        let result = res.data;
        if (result.code === 'C200') {
          this.user = result.data;
        } else {
          this.$message.error("系统出错，稍后重试");
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
.el-header, .el-footer {
  background-color: #409EFF;
  color: #000;
  text-align: center;
  line-height: 60px;
}

.el-aside {
  background-color: #D3DCE6;
  color: #333;
  text-align: center;
  line-height: 200px;
}

.el-main {
  background-color: #FFFFFF;
  color: #333;
  text-align: center;
  line-height: 60px;
}

body > .el-container {
  margin-bottom: 40px;
}

.el-container:nth-child(5) .el-aside,
.el-container:nth-child(6) .el-aside {
  line-height: 260px;
}

.el-container:nth-child(7) .el-aside {
  line-height: 320px;
}
</style>
