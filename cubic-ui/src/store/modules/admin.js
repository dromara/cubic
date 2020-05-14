import { userInfo, loginName, logout } from "../../api/auth/login";
import * as types from "../mutation-types";
import { constantRouterMap } from "../../router";
import {
    getToken,
    setToken,
    removeToken,
    getAdminId,
    setAdminId,
    removeAdminId
} from "../../utils/auth";
// import { $NOT_NETWORK } from '../../utils/errorCode'
import { Message } from "element-ui";

// initial state
const state = {
    adminId: getAdminId(), // id
    userName: "", // 昵称
    avatar: "", // 头像
    token: getToken(), // 登录token
    authRules: [], // 权限列表
    routers: constantRouterMap // 路由列表
};

// getters
const getters = {
    adminId: state => state.adminId,
    userName: state => state.userName,
    avatar: state => state.avatar,
    token: state => state.token,
    authRules: state => state.authRules,
    routers: state => state.routers
};

// actions
const actions = {
    // 用户名登录
    loginName({ commit }, userInfo) {
        const userName = userInfo.userName ? userInfo.userName.trim() : "";
        const pwd = userInfo.pwd ? userInfo.pwd : "";
        return new Promise((resolve, reject) => {
            loginName(userName, pwd)
                .then(response => {
                    if (response.code) {
                        Message({
                            message: response.message,
                            type: "error",
                            duration: 5 * 1000
                        });
                    } else {
                        let data = response.data;
                        commit(types.RECEIVE_ADMIN_ID, data.id);
                        commit(types.RECEIVE_ADMIN_TOKEN, data.token);
                        commit(types.RECEIVE_ADMIN_AUTH_RULES, []);
                    }
                    resolve(response);
                })
                .catch(error => {
                    reject(error);
                });
        });
    },
    userInfo({ commit }) {
        return new Promise((resolve, reject) => {
            userInfo()
                .then(response => {
                    if (response.code === 2) {
                        reject("登录失效");
                    }
                    const data = response.data || {};
                    commit(types.RECEIVE_ADMIN_NAME, data.username);
                    commit(types.RECEIVE_ADMIN_AVATAR, data.avatar);
                    commit(types.RECEIVE_ADMIN_AUTH_RULES, data.authRules);
                    resolve(data);
                })
                .catch(error => {
                    reject(error);
                });
        });
    },
    // 登出
    loginOut({ commit }) {
        return new Promise((resolve, reject) => {
            logout()
                .then(() => {
                    commit(types.RECEIVE_ADMIN_ID, "");
                    commit(types.RECEIVE_ADMIN_TOKEN, "");
                    commit(types.RECEIVE_ADMIN_AUTH_RULES, []);
                    resolve();
                })
                .catch(error => {
                    reject(error);
                });
        });
    },

    // 前端 登出
    fedLogout({ commit }) {
        return new Promise(resolve => {
            commit(types.RECEIVE_ADMIN_ID, "");
            commit(types.RECEIVE_ADMIN_TOKEN, "");
            commit(types.RECEIVE_ADMIN_AUTH_RULES, []);
            resolve();
        });
    },
    /**
     * 过滤路由列表
     * @param commit
     * @param data
     * @returns {Promise}
     */
    filterRouter({ commit }, data) {
        const { accessedRouters } = data;
        if (accessedRouters && accessedRouters.length > 0) {
            commit(types.RECEIVE_ROUTERS, accessedRouters);
        }
    }
};

// mutations
const mutations = {
    [types.RECEIVE_ADMIN_ID](state, adminId) {
        state.adminId = adminId;
        if (adminId === "") {
            removeAdminId();
        } else {
            setAdminId(adminId);
        }
    },
    [types.RECEIVE_ADMIN_TOKEN](state, token) {
        state.token = token;
        if (token === "") {
            removeToken();
        } else {
            setToken(token);
        }
    },
    [types.RECEIVE_ADMIN_NAME](state, userName) {
        state.userName = userName;
    },
    [types.RECEIVE_ADMIN_AVATAR](state, avatar) {
        state.avatar = avatar;
    },
    [types.RECEIVE_ADMIN_AUTH_RULES](state, authRules) {
        state.authRules = authRules;
    },
    [types.RECEIVE_ROUTERS](state, routers) {
        const tempRm = constantRouterMap.concat(routers);
        state.routers = JSON.parse(JSON.stringify(tempRm));
    }
};

export default {
    state,
    getters,
    actions,
    mutations
};
