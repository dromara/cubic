import "babel-polyfill"; // 解决Ie9
import Vue from "vue";
import App from "./App.vue";
import router from "./router/index";
import store from "./store/index";
import "./element";
import * as filters from "./filtres/index"; // 全局过滤器
import "./role"; // 权限

import "./mock"; // 模拟数据

import "./assets/icons/iconfont";
import IconSvg from "./components/common/IconSvg.vue"; // svg组件

// 注册全局组件（register global）
Vue.component("icon-svg", IconSvg);

// 注册全局实用程序过滤器（register global utility filters）.
Object.keys(filters).forEach(key => {
    Vue.filter(key, filters[key]);
});

Vue.config.productionTip = false;
new Vue({
    router,
    store,
    render: h => h(App)
}).$mount("#app");
