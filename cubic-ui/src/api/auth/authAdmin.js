/**
 * Created by lk on 17/6/4.
 */
import axios from "../../utils/axios";

// 获取列表
export function authAdminList(query) {
    return axios({
        url: "/admin/auth/admin/index",
        method: "get",
        params: query
    });
}

// 获取角色列表
export function authAdminRoleList(query) {
    return axios({
        url: "/admin/auth/admin/roleList",
        method: "get",
        params: query
    });
}

// 保存
export function authAdminSave(data, formName, method = "post") {
    let url =
        formName === "add"
            ? "/admin/auth/admin/save"
            : "/admin/auth/admin/edit";
    return axios({
        url: url,
        method: method,
        data: data
    });
}

// 删除管理员
export function authAdminDelete(data) {
    return axios({
        url: "/admin/auth/admin/delete",
        method: "post",
        data: data
    });
}
