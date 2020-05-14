/**
 * Created by lk on 17/6/4.
 */
import axios from "../../utils/axios";

// 谁最懂我相关

// 列表
export function adSiteList(query) {
    return axios({
        url: "/admin/ad/site/index",
        method: "get",
        params: query
    });
}

// 广告列表
export function adSiteAdList(data) {
    return axios({
        url: "/admin/ad/site/adList",
        method: "post",
        data: data
    });
}

// 保存
export function adSiteSave(data, formName, method = "post") {
    var url =
        formName === "add" ? "/admin/ad/site/save" : "/admin/ad/site/edit";
    return axios({
        url: url,
        method: method,
        data: data
    });
}

// 删除
export function adSiteDelete(data) {
    return axios({
        url: "/admin/ad/site/delete",
        method: "post",
        data: data
    });
}
