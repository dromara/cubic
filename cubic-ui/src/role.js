import router from "./router/index";
import store from "./store/index";
import NProgress from "nprogress"; // Progress 进度条
import "nprogress/nprogress.css"; // Progress 进度条样式
import { getAdminId } from "./utils/auth"; // 验权
import { Message } from "element-ui";
import { asyncRouterMap } from "./router/index";
// permissiom judge
function hasRole(authRules, permissionAuthRules) {
    if (!authRules || authRules.length <= 0) {
        return false;
    }
    if (authRules.indexOf("admin") >= 0) return true; // admin权限 直接通过
    if (!permissionAuthRules) return true;
    return authRules.some(role => permissionAuthRules.indexOf(role) >= 0);
}

/**
 * 通过meta.role判断是否与当前用户权限匹配
 * @param authRules
 * @param route
 */
function hasRouterRole(authRules, route) {
    if (
        authRules.indexOf("admin") >= 0 ||
        !route.meta ||
        !route.meta.authRule
    ) {
        return true;
    }
    return authRules.some(
        authRule => route.meta.authRule.indexOf(authRule) >= 0
    );
}

/**
 * 递归过滤异步路由表，返回符合用户角色权限的路由表
 * @param asyncRouterMap
 * @param authRules
 */
function filterAsyncRouter(asyncRouterMap, authRules) {
    const accessedRouters = asyncRouterMap.filter(route => {
        if (hasRouterRole(authRules, route)) {
            if (route.children && route.children.length) {
                route.children = filterAsyncRouter(route.children, authRules);
            }
            return true;
        }
        return false;
    });
    return accessedRouters;
}

// register global progress.
const whiteList = ["/login", "/401", "/404", "/500"]; // 不重定向白名单
router.beforeEach((to, from, next) => {
    NProgress.start(); // 开启Progress
    if (whiteList.indexOf(to.path) !== -1) {
        // 在免登录白名单，直接进入
        next();
        return;
    }
    let adminId = getAdminId();
    if (adminId !== "undefined" && adminId !== "" && adminId) {
        // 判断是否有token
        if (to.path === "/login") {
            next({ path: "/" });
            NProgress.done(); // router在hash模式下 手动改变hash 重定向回来 不会触发afterEach 暂时hack方案 ps：history模式下无问题，可删除该行！
            return;
        }
        if (
            !store.getters.userName &&
            (!store.getters.authRules || store.getters.authRules.length === 0)
        ) {
            // 判断当前用户是否已拉取完用户信息
            store
                .dispatch("userInfo")
                .then(data => {
                    // 拉取user_info
                    const authRules = data.authRules || [];
                    if (
                        !(authRules instanceof Array) ||
                        authRules.length === 0
                    ) {
                        Message.error("权限验证失败，请联系管理员~");
                        next({
                            path: "/401",
                            query: { noGoBack: true }
                        });
                        NProgress.done();
                        return;
                    }
                    let accessedRouters = filterAsyncRouter(
                        asyncRouterMap,
                        authRules
                    );
                    // 生成可访问的路由表
                    router.addRoutes(accessedRouters); // 动态添加可访问路由表
                    next({ ...to }); // hack方法 确保addRoutes已完成
                    // 设置左边导航栏
                    store
                        .dispatch("filterRouter", { accessedRouters })
                        .then(() => {});
                })
                .catch(() => {
                    store.dispatch("fedLogout").then(() => {
                        Message.error("验证失败,请重新登录");
                        let redirect = to.fullPath;
                        store.dispatch("loginOut").then(() => {
                            next({
                                path: "/login",
                                query: { redirect: redirect }
                            });
                        });
                    });
                });
            return;
        }
        // 没有动态改变权限的需求可直接next() 删除下方权限判断 ↓
        if (hasRole(store.getters.authRules, to.meta.authRule)) {
            next(); //
            return;
        }
        next({
            path: "/401",
            query: { noGoBack: true }
        });
        NProgress.done(); // router在hash模式下 手动改变hash 重定向回来 不会触发afterEach 暂时hack方案 ps：history模式下无问题，可删除该行！
        return;
    }
    let redirect = to.fullPath;
    store.dispatch("loginOut").then(() => {
        next({
            path: "/login",
            query: { redirect: redirect }
        });
    }); // 否则全部重定向到登录页
    NProgress.done(); // router在hash模式下 手动改变hash 重定向回来 不会触发afterEach 暂时hack方案 ps：history模式下无问题，可删除该行！
});

router.afterEach(() => {
    NProgress.done(); // 结束Progress
});
