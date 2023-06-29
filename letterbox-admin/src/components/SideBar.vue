<template>
  <el-menu style="min-height: 100%; overflow-x: hidden; text-align: left"
           class="el-menu-vertical"
           :collapse-transition="true"
           :collapse="isCollapsed" 
           router
           :default-active="'/home'"
           >
    <el-submenu :index="String(menu.id)" v-for="menu in menus" :key="menu.id">
      <template slot="title">
        <i :class="menu.icon"></i>
        <span slot="title">{{menu.menuName}}</span>
      </template>
      <el-menu-item :index="'/'+cmenu.path" v-for="cmenu in menu.children" :key="cmenu.id">
        <i :class="cmenu.icon"></i>
        <span slot="title">{{cmenu.menuName}}</span>
      </el-menu-item>
    </el-submenu>
  </el-menu>
</template>

<script>
export default {
  name: "SideBar",
  props: {
    isCollapsed: Boolean,
    menus: Array
  }
}
</script>

<style>
</style>
