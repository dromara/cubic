import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: { title: '应用中心' }
    }]
  },
  {
    path: '/app',
    component: Layout,
    redirect: '/base',
    children: [{
      path: 'base',
      name: 'Base',
      component: () => import('@/views/app/base/index'),
      meta: { title: '基础信息'  }
    }]
  },
  {
    path: '/command',
    component: Layout,
    redirect: '/command',
    children: [{
      path: 'command',
      name: 'WebShell',
      component: () => import('@/views/command/cmd'),
      meta: { title: 'Arthas工具' }
    }]
  },
  {
    path: '/dump',
    component: Layout,
    redirect: '/dump',
    children: [{
      path: 'dump',
      name: 'Dump',
      component: () => import('@/views/dump/index'),
      meta: { title: '线程栈监控' }
    }]
  },
  // {
  //   path: '/app',
  //   component: Layout,
  //   redirect: '/app/command',
  //   name: 'app',
  //   meta: { title: '应用中心', icon: 'example', activeMenu: '/article/list' },
  //   children: [
  //     {
  //       path: 'list',
  //       name: 'List',
  //       component: () => import('@/views/app/list/index'),
  //       meta: { title: '应用实例', icon: 'tree' }
  //     },
  //     {
  //       path: 'base',
  //       name: 'Base',
  //       component: () => import('@/views/app/base/index'),
  //       meta: {
  //         title: '基础信息',
  //         icon: 'table'
  //       }
  //     },
  //     {
  //       path: 'command',
  //       name: 'WebShell',
  //       component: () => import('@/views/app/command/cmd'),
  //       meta: { title: 'Arthas', icon: 'tree' }
  //     }
  //   ]
  // },
  // {
  //   path: '/form',
  //   component: Layout,
  //   children: [
  //     {
  //       path: 'index',
  //       name: 'Form',
  //       component: () => import('@/views/form/index'),
  //       meta: { title: '服务监控', icon: 'form' }
  //     }
  //   ]
  // },

  // {
  //   path: '/nested',
  //   component: Layout,
  //   redirect: '/nested/menu1',
  //   name: 'Nested',
  //   meta: {
  //     title: '工具',
  //     icon: 'nested'
  //   },
  //   children: [
  //     {
  //       path: 'menu1',
  //       component: () => import('@/views/nested/menu1/index'), // Parent router-view
  //       name: 'Dump分析',
  //       meta: { title: 'Menu1' },
  //       children: [
  //
  //         {
  //           path: 'menu1-2',
  //           component: () => import('@/views/nested/menu1/menu1-2'),
  //           name: 'Thread Dump分析',
  //           meta: { title: 'Menu1-2' }
  //           // children: [
  //           //   {
  //           //     path: 'menu1-2-1',
  //           //     component: () => import('@/views/nested/menu1/menu1-2/menu1-2-1'),
  //           //     name: 'Head Dump分析',
  //           //     meta: { title: 'Menu1-2-1' }
  //           //   },
  //           //   {
  //           //     path: 'menu1-2-2',
  //           //     component: () => import('@/views/nested/menu1/menu1-2/menu1-2-2'),
  //           //     name: 'Menu1-2-2',
  //           //     meta: { title: 'Menu1-2-2' }
  //           //   }
  //           // ]
  //         },
  //         {
  //           path: 'menu1-3',
  //           component: () => import('@/views/nested/menu1/menu1-3'),
  //           name: 'XX分析',
  //           meta: { title: 'Menu1-3' }
  //         }
  //       ]
  //     },
  //     {
  //       path: 'menu1-1',
  //       component: () => import('@/views/nested/menu1/menu1-1'),
  //       name: 'Menu1-1',
  //       meta: { title: 'Menu1-1' }
  //     },
  //     {
  //       path: 'menu2',
  //       name: 'JVM参数生成',
  //       component: () => import('@/views/nested/menu2/index'),
  //       meta: { title: 'menu2' }
  //     }
  //   ]
  // },
  // {
  //   path: 'external-link',
  //   component: Layout,
  //   children: [
  //     {
  //       path: 'https://www.jiagoujishu.com',
  //       meta: { title: '实例列表', icon: 'link' }
  //     }
  //   ]
  // },
  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
