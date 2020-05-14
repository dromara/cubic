import { getStore, setStore, removeStore } from "./store";

const adminId = "ADMIN-ID";
const adminToken = "ADMIN-TOKEN";

// 获取token
export function getToken() {
    return getStore(adminToken);
}

// 设置token
export function setToken(sid) {
    return setStore(adminToken, sid, 365);
}

// 删除token
export function removeToken() {
    return removeStore(adminToken);
}

// 获取admin_id
export function getAdminId() {
    return getStore(adminId);
}

// 设置admin_id
export function setAdminId(id) {
    return setStore(adminId, id, 365);
}

// 删除admin_id
export function removeAdminId() {
    return removeStore(adminId);
}
