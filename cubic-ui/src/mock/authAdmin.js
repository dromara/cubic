/**
 * Created by lk on 18/4/28.
 */

const index = {
    data: {
        total: 1,
        list: [
            {
                id: 1,
                username: "admin",
                avatar: null,
                tel: "admin",
                email: "lmxdawn@gmail.com",
                status: 1,
                lastLoginIp: "127.0.0.1",
                lastLoginTime: 1493103488,
                createTime: 1487868050,
                roles: []
            }
        ]
    }
};

const roleList = {
    data: {
        total: 1,
        list: [
            {
                id: 1,
                name: "超级管理员"
            }
        ]
    }
};

const save = {
    data: {
        id: "2",
        username: "test",
        password: "",
        status: "1",
        roles: [1]
    }
};

const edit = {
    code: 0,
    message: "success"
};

const del = {
    code: 0,
    message: "success"
};

export default {
    index,
    roleList,
    save,
    edit,
    del
};
