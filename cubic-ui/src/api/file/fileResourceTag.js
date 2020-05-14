/**
 * 资源分组相关
 */
import axios from "../../utils/axios";

// 列表
export function fileResourceTagList(query) {
    return axios({
        url: "/admin/file/resource_tag/index",
        method: "get",
        params: query
    });
}

// 创建分组
export function fileResourceTagAdd(data) {
    return axios({
        url: "/admin/file/resource_tag/add",
        method: "post",
        data: data
    });
}
