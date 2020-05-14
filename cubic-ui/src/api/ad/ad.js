/**
 * Created by lk on 17/6/4.
 */
import axios from "../../utils/axios";

// 谁最懂我相关

// 列表
export function adList(query) {
    return axios({
        url: "/admin/ad/ad/index",
        method: "get",
        params: query
    });
}

// 保存
export function adSave(data, formName, method = "post") {
    var url = formName === "add" ? "/admin/ad/ad/save" : "/admin/ad/ad/edit";
    return axios({
        url: url,
        method: method,
        data: data
    });
}

// 删除
export function adDelete(data) {
    return axios({
        url: "/admin/ad/ad/delete",
        method: "post",
        data: data
    });
}
