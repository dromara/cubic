<template>
  <div :class="classObj" class="app-wrapper">
    <topbar />
    <div v-if="device==='mobile'&&sidebar.opened" class="drawer-bg" @click="handleClickOutside" />
    <!-- <sidebar v-if="!sidebar.hide" class="sidebar-container" /> -->
    <left-bar :class="LeftBarShow ? ' sidebar-container' : 'sidebar-container disnone'" />
    <div :class="{sidebarHide: sidebar.hide, marginnone: !LeftBarShow}" class="main-container">
      <div :class="{'fixed-header':fixedHeader}">
        <navbar />
      </div>
      <app-main />
    </div>
  </div>
</template>

<script>
import { Navbar, Sidebar, AppMain, Topbar, LeftBar } from './components'
import ResizeMixin from './mixin/ResizeHandler'

export default {
  name: 'Layout',
  data() {
    return {
      LeftBarShow: true
    }
  },
  components: {
    Navbar,
    Sidebar,
    AppMain,
    Topbar,
    'left-bar': LeftBar
  },
  mixins: [ResizeMixin],
  computed: {
    sidebar() {
      return this.$store.state.app.sidebar
    },
    device() {
      return this.$store.state.app.device
    },
    fixedHeader() {
      return this.$store.state.settings.fixedHeader
    },
    classObj() {
      return {
        hideSidebar: !this.sidebar.opened,
        openSidebar: this.sidebar.opened,
        withoutAnimation: this.sidebar.withoutAnimation,
        mobile: this.device === 'mobile'
      }
    }
  },
  methods: {
    handleClickOutside() {
      this.$store.dispatch('app/closeSideBar', { withoutAnimation: false })
    }
  },
  watch: {
    $route(to) {
      this.LeftBarShow = to.meta.showMenu
    }
  },
  mounted() {
    this.LeftBarShow = this.$route.meta.showMenu
  }
}
</script>

<style lang="scss" scoped>
  @import "~@/styles/mixin.scss";
  @import "~@/styles/variables.scss";

  .app-wrapper {
    @include clearfix;
    position: relative;
    // height: 100%;
    height: $contentHeight;
    width: 100%;
    &.mobile.openSidebar{
      position: fixed;
      top: 0;
    }
  }
  .drawer-bg {
    background: #000;
    opacity: 0.3;
    width: 100%;
    top: 0;
    height: 100%;
    position: absolute;
    z-index: 999;
  }

  .fixed-header {
    position: fixed;
    top: 0;
    right: 0;
    z-index: 9;
    width: calc(100% - #{$sideBarWidth});
    transition: width 0.28s;
  }

  .hideSidebar .fixed-header {
    width: calc(100% - 210px)
  }

  .mobile .fixed-header {
    width: 100%;
  }

  .disnone {
    display: none;
  }

  .marginnone {
    margin: 0 !important;
  }
</style>
