/**
 * Created by lk on 18/4/28.
 */

// 获取七牛云
const qiuNiuUpToken = {
    data: {
        domain: "https://res.xxxxx.cn",
        expires_in: 86400,
        upToken:
            "GRUuplbSg1DrFbLlx2UPc0Qe0oIcIdqVRPgQ6i:nPJdU3WUHFcu5MSc4EOY8RSThGE=:eyJzY29wZSI6InNoaWd1YW5namkiLCJkZWFkbGluZSI6MTU0MzQ2NzcyN30=",
        uploadUrl: "/admin/file/upload/createFile"
    }
};

// 上传文件
const createFile = {
    key: "TIMjieTu_20180516105525-fu_ben_.png"
};

export default {
    qiuNiuUpToken,
    createFile
};
