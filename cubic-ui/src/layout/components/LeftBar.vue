<template>
  <div :v-if="$route.meta.showMenu" class="leftbar">
    <p class="center">
        功能列表
    </p>
    <el-menu
    router
    :default-active="activeMenu"
    >
    <el-menu-item index="/app/base">
        <i class="el-icon-document"></i>
        <span slot="title">基础信息</span>
    </el-menu-item>
    <el-menu-item index="/command/command">
        <i class="el-icon-edit"></i>
        <span slot="title">Arthas工具</span>
    </el-menu-item>
    <el-menu-item index="/dump/dump">
        <i class="el-icon-data-analysis"></i>
        <span slot="title">线程栈监控</span>
    </el-menu-item>
    <el-menu-item index="/threadpool/threadpool">
        <i class="el-icon-data-line"></i>
        <span slot="title">线程池监控</span>
    </el-menu-item>
    <el-menu-item index="/clash/clash">
        <i class="el-icon-warning"></i>
        <span slot="title">依赖冲突预警</span>
    </el-menu-item>
    <el-submenu index="/system">
        <template slot="title">
          <i class="el-icon-setting"></i>
          <span>系统管理</span>
        </template>
        <el-menu-item-group class="margin-left">
          <el-menu-item index="/system/userManage">用户管理</el-menu-item>
        </el-menu-item-group>
      </el-submenu>
    </el-menu>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import variables from '@/styles/variables.scss'

export default {
  name: 'left-bar',
  computed: {
    ...mapGetters([
      'sidebar'
    ]),
    activeMenu() {
      const activeMenu = this.$route.path
      return activeMenu
    },
    variables() {
      return variables
    }
  },
  methods: {
    handleSelect(key, keyPath) {
      // 把选中路由的子路由保存store
      const route = this.routes.find(item => item.path === key)
      this.$store.commit('permission/SET_CURRENT_ROUTES', route)
      // this.setSidebarHide(route)
    },
  }
}
</script>

<style lang="scss" scoped>
.leftbar {
    width: 210px !important;
    // background-color: #3d5b80;
    .center {
        text-align: center;
        line-height: 40px;
        // background-color: rgb(225, 239, 255);
        margin: 0;
        padding: 16px;
        color: #1b1b1b;
    }
}
.margin-left {
  margin-left: 30px;
}
::v-deep .el-menu-item-group__title {
  padding: 0 !important;
}
</style>
