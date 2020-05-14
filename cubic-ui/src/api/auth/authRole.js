/**
 * Created by lk on 17/6/4.
 */
import axios from "../../utils/axios";

// 获取列表
export function authRoleList(query) {
    return axios({
        url: "/admin/auth/role/index",
        method: "get",
        params: query
    });
}

// 编辑
export function authRoleAuthList(query) {
    return axios({
        url: "/admin/auth/role/authList",
        method: "get",
        params: query
    });
}

// 添加
export function authRoleAuth(data) {
    return axios({
        url: "/admin/auth/role/auth",
        method: "post",
        data: data
    });
}

// 保存
export function authRoleSave(data, formName, method = "post") {
    let url =
        formName === "add" ? "/admin/auth/role/save" : "/admin/auth/role/edit";
    return axios({
        url: url,
        method: method,
        data: data
    });
}

// 删除
export function authRoleDelete(data) {
    return axios({
        url: "/admin/auth/role/delete",
        method: "post",
        data: data
    });
}
