/**
 * 资源管理相关
 */
import axios from "../../utils/axios";

// 列表
export function fileResourceList(query) {
    return axios({
        url: "/admin/file/resource/index",
        method: "get",
        params: query
    });
}
// 添加
export function fileResourceAdd(data) {
    return axios({
        url: "/admin/file/resource/add",
        method: "POST",
        data: data
    });
}
