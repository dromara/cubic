import axios from "axios";
import { Message } from "element-ui";
import store from "../store/index";
import { BASE_URL } from "../config/app";
import router from "../router/index";

// 创建axios实例
const service = axios.create({
    baseURL: BASE_URL, // api的base_url
    timeout: 5000 // 请求超时时间
});

// request拦截器
service.interceptors.request.use(
    config => {
        // Do something before request is sent
        if (store.getters.adminId && store.getters.token) {
            config.headers["X-Adminid"] = store.getters.adminId;
            config.headers["X-Token"] = store.getters.token;
        }
        return config;
    },
    error => {
        // Do something with request error
        Promise.reject(error);
    }
);

// respone拦截器
service.interceptors.response.use(
    response => {
        const data = response.data;
        if (data.code) {
            if (data.code === 2) {
                store.dispatch("fedLogout").then(() => {
                    Message.error("验证失败,请重新登录");
                    router.push({
                        path: "/login",
                        query: { redirect: router.currentRoute.fullPath } // 从哪个页面跳转过来
                    });
                });
            }
        }
        return data;
    },
    error => {
        Message({
            message: error.message,
            type: "error",
            duration: 5 * 1000
        });
        return Promise.reject(error);
    }
);

export default service;
